package com.example.sqlite.func;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.API.ApiClient;
import com.example.sqlite.API.ApiService;
import com.example.sqlite.R;
import com.example.sqlite.adapter.BudgetAdapter;
import com.example.sqlite.model.Budget;
import com.example.sqlite.model.Category;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BudgetActivity extends AppCompatActivity {

    private Spinner spinnerCategory;
    private EditText edtAmount, edtStartDate, edtEndDate;
    private Button btnSave;
    private ApiService apiService;
    private List<Category> categoryList;
    private List<Budget> budgetList = new ArrayList<>();
    private String currentUser;
    private BudgetAdapter adapter;

    private Budget editingBudget = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        // Ánh xạ
        spinnerCategory = findViewById(R.id.spinnerCategory);
        edtAmount = findViewById(R.id.edtAmount);
        edtStartDate = findViewById(R.id.edtStartDate);
        edtEndDate = findViewById(R.id.edtEndDate);
        btnSave = findViewById(R.id.btnSave);


        adapter = new BudgetAdapter(new ArrayList<>(), new BudgetAdapter.OnBudgetActionListener() {
            @Override
            public void onEdit(Budget budget) {
                // Gán dữ liệu vào form
                edtAmount.setText(String.valueOf(budget.getAmount()));
                edtStartDate.setText(budget.getStartDate());
                edtEndDate.setText(budget.getEndDate());

                // Đặt lại spinner
                for (int i = 0; i < categoryList.size(); i++) {
                    if (categoryList.get(i).getId() == budget.getCategoryId()) {
                        spinnerCategory.setSelection(i);
                        break;
                    }
                }

                // Gắn cờ "đang sửa"
                editingBudget = budget;
                btnSave.setText("Cập nhật ngân sách");
            }

            @Override
            public void onDelete(Budget budget) {
                apiService.deleteBudget(budget.getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(BudgetActivity.this, "Đã xoá ngân sách", Toast.LENGTH_SHORT).show();
                            loadBudgets(); // Reload lại sau khi xoá
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(BudgetActivity.this, "Lỗi xoá ngân sách", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        // RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerViewBudget);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // API & User
        apiService = ApiClient.getClient().create(ApiService.class);
        currentUser = getSharedPreferences("UserSession", MODE_PRIVATE)
                .getString("username", "");

        loadCategories();
        loadBudgets();

        btnSave.setOnClickListener(v -> saveBudget());

        edtStartDate.setOnClickListener(v -> showDatePickerDialog(edtStartDate));
        edtEndDate.setOnClickListener(v -> showDatePickerDialog(edtEndDate));


    }

    private void loadCategories() {
        apiService.getAllCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryList = response.body(); // Đã sửa đúng ở đây
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            BudgetActivity.this,
                            android.R.layout.simple_spinner_item,
                            getCategoryNames(categoryList)
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCategory.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(BudgetActivity.this, "Lỗi khi tải loại chi tiêu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadBudgets() {
        apiService.getBudgetsByUser(currentUser).enqueue(new Callback<List<Budget>>() {
            @Override
            public void onResponse(Call<List<Budget>> call, Response<List<Budget>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    budgetList.clear();
                    budgetList.addAll(response.body());
                    adapter.setBudgetList(budgetList);

                    // Kiểm tra vượt ngân sách sau khi đã load xong
                    for (Budget b : budgetList) {
                        checkBudgetExceeded(b);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Budget>> call, Throwable t) {
                Toast.makeText(BudgetActivity.this, "Lỗi tải ngân sách", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void saveBudget() {
        if (categoryList == null || categoryList.isEmpty()) {
            Toast.makeText(BudgetActivity.this, "Chưa có loại chi tiêu!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int categoryId = categoryList.get(spinnerCategory.getSelectedItemPosition()).getId();
            double amount = Double.parseDouble(edtAmount.getText().toString());
            String startDate = edtStartDate.getText().toString();
            String endDate = edtEndDate.getText().toString();

            if (editingBudget != null) {
                // 👉 Đây là CẬP NHẬT
                editingBudget.setCategoryId(categoryId);
                editingBudget.setAmount(amount);
                editingBudget.setStartDate(startDate);
                editingBudget.setEndDate(endDate);

                apiService.updateBudget(
                        editingBudget.getId(),
                        editingBudget.getCategoryId(),
                        editingBudget.getAmount(),
                        editingBudget.getStartDate(),
                        editingBudget.getEndDate(),
                        editingBudget.getUser()
                ).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Toast.makeText(BudgetActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        editingBudget = null;
                        btnSave.setText("Lưu ngân sách");
                        clearForm();
                        loadBudgets();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(BudgetActivity.this, "Lỗi cập nhật ngân sách", Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                // 👉 Đây là THÊM MỚI
                Budget budget = new Budget();
                budget.setCategoryId(categoryId);
                budget.setAmount(amount);
                budget.setStartDate(startDate);
                budget.setEndDate(endDate);
                budget.setUser(currentUser);

                apiService.insertBudget(
                        budget.getCategoryId(),
                        budget.getAmount(),
                        budget.getStartDate(),
                        budget.getEndDate(),
                        budget.getUser()
                ).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(BudgetActivity.this, "Thêm ngân sách thành công", Toast.LENGTH_SHORT).show();
                            clearForm();
                            loadBudgets();
                        } else {
                            Toast.makeText(BudgetActivity.this, "Thêm ngân sách thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(BudgetActivity.this, "Lỗi kết nối máy chủ", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        } catch (Exception e) {
            Toast.makeText(BudgetActivity.this, "Vui lòng nhập đầy đủ và đúng định dạng", Toast.LENGTH_SHORT).show();
        }
    }

    private List<String> getCategoryNames(List<Category> categories) {
        List<String> names = new ArrayList<>();
        for (Category c : categories) {
            names.add(c.getName());
        }
        return names;
    }

    private void showDatePickerDialog(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Định dạng lại thành yyyy-MM-dd
                    String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                    editText.setText(selectedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
    }
    private void checkBudgetExceeded(Budget budget) {
        apiService.getTotalExpenseInBudget(
                budget.getUser(),
                budget.getCategoryId(),
                budget.getStartDate(),
                budget.getEndDate()
        ).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        double totalExpense = response.body().get("total").getAsDouble();
                        if (totalExpense > budget.getAmount()) {
                            Toast.makeText(BudgetActivity.this,
                                    "⚠ Chi tiêu vượt ngân sách cho danh mục: " + budget.getCategoryName()
                                            + "\nĐã chi: " + totalExpense + ", Ngân sách: " + budget.getAmount(),
                                    Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(BudgetActivity.this, "Lỗi xử lý dữ liệu từ máy chủ", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BudgetActivity.this, "Không lấy được dữ liệu chi tiêu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("BUDGET_ERROR", "Lỗi kết nối kiểm tra ngân sách", t);

                Toast.makeText(BudgetActivity.this, "Lỗi kết nối kiểm tra ngân sách", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearForm() {
        edtAmount.setText("");
        edtStartDate.setText("");
        edtEndDate.setText("");
        spinnerCategory.setSelection(0);
        editingBudget = null;
        btnSave.setText("Lưu ngân sách");
    }
}
