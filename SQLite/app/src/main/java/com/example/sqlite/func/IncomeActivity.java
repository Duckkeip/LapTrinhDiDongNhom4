
package com.example.sqlite.func;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlite.API.ApiClient;
import com.example.sqlite.API.ApiService;
import com.example.sqlite.MainActivity_Final;
import com.example.sqlite.R;
import com.example.sqlite.adapter.IncomeAdapter;
import com.example.sqlite.model.Category;
import com.example.sqlite.model.Income;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import org.json.JSONObject;


public class IncomeActivity extends AppCompatActivity {

    private EditText etAmount, etNote;
    private Button btnSaveIncome, btnBackMainfinal;
    private RecyclerView recyclerViewIncome;
    private IncomeAdapter incomeAdapter;
    private List<Income> incomeList;

    private String currentUser;
    private ApiService apiService;

    Spinner spinnerCategory;
    List<Category> categoryList = new ArrayList<>();
    Category selectedCategory;
    private static final String TAG = "IncomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        currentUser = prefs.getString("username", "");

        apiService = ApiClient.getClient().create(ApiService.class);

        etAmount = findViewById(R.id.etAmount);
        etNote = findViewById(R.id.etNote);
        btnSaveIncome = findViewById(R.id.btnSaveIncome);
        btnBackMainfinal = findViewById(R.id.btnBack);
        recyclerViewIncome = findViewById(R.id.recyclerViewIncome);
        recyclerViewIncome.setLayoutManager(new LinearLayoutManager(this));
        spinnerCategory = findViewById(R.id.spinnerCategory);

        incomeList = new ArrayList<>();
        incomeAdapter = new IncomeAdapter(incomeList, new IncomeAdapter.OnItemActionListener() {
            @Override
            public void onDelete(Income income) {
                new AlertDialog.Builder(IncomeActivity.this)
                        .setTitle("Xác nhận xoá")
                        .setMessage("Bạn có chắc muốn xoá khoản thu \"" + income.getAmount() + "\" này?")
                        .setPositiveButton("Xoá", (dialog, which) -> {
                            Log.d(TAG, "onDelete: Gọi API xoá ID = " + income.getId());
                            apiService.deleteIncome(income.getId()).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    try {
                                        if (response.isSuccessful() && response.body() != null) {
                                            String body = response.body().string();
                                            Log.d(TAG, "Delete response: " + body);
                                            JSONObject obj = new JSONObject(body);
                                            if (obj.getString("status").equals("success")) {
                                                Toast.makeText(IncomeActivity.this, "Đã xoá thành công", Toast.LENGTH_SHORT).show();
                                                loadIncomeList(currentUser);
                                            } else {
                                                Toast.makeText(IncomeActivity.this, "Xoá thất bại: " + obj.getString("message"), Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(IncomeActivity.this, "Xoá thất bại: " + response.code(), Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        Log.e(TAG, "Lỗi xử lý JSON khi xoá", e);
                                        Toast.makeText(IncomeActivity.this, "Lỗi xử lý dữ liệu xoá", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Log.e(TAG, "onDelete: Lỗi kết nối xoá", t);
                                    Toast.makeText(IncomeActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                                }
                            });
                        })
                        .setNegativeButton("Huỷ", null)
                        .show();
            }

            @Override
            public void onEdit(Income income) {
                Log.d(TAG, "onEdit: Hiển thị dialog chỉnh sửa income ID = " + income.getId());
                showEditDialog(income);  // goi edit cac khoan
            }
        });

        apiService.getIncomeCategories("income").enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    categoryList = response.body();
                    ArrayAdapter<Category> adapter = new ArrayAdapter<>(
                            IncomeActivity.this, android.R.layout.simple_spinner_item, categoryList
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCategory.setAdapter(adapter);

                    spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            selectedCategory = categoryList.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {}
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(IncomeActivity.this, "Lỗi khi tải danh mục", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewIncome.setAdapter(incomeAdapter);

        btnSaveIncome.setOnClickListener(v -> saveIncome());
        btnBackMainfinal.setOnClickListener(v -> Back());

        loadIncomeList(currentUser);
    }
    private void showEditDialog(Income income) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_income, null);
        EditText etEditAmount = view.findViewById(R.id.etEditAmount);
        EditText etEditNote = view.findViewById(R.id.etEditNote);

        etEditAmount.setText(String.valueOf(income.getAmount()));
        etEditNote.setText(income.getNote());
        Spinner spinnerEditCategory = view.findViewById(R.id.spinnerEditCategory);


        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEditCategory.setAdapter(adapter);

// Set mặc định là danh mục đang có
        for (int i = 0; i < categoryList.size(); i++) {
            if (categoryList.get(i).getId() == income.getCategoryId()) {
                spinnerEditCategory.setSelection(i);
                break;
            }
        }
        new AlertDialog.Builder(this)
                .setTitle("Chỉnh sửa khoản thu")
                .setView(view)

                .setPositiveButton("Lưu", (dialog, which) -> {
                    try {
                        String newAmountStr = etEditAmount.getText().toString().trim();
                        String newNote = etEditNote.getText().toString().trim();

                        if (TextUtils.isEmpty(newAmountStr)) {
                            Toast.makeText(this, "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        float newAmount = Float.parseFloat(newAmountStr);
                        Log.d(TAG, "Cập nhật income ID = " + income.getId());

                        String newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
                        Category selectedCategory = (Category) spinnerEditCategory.getSelectedItem();
                        int categoryId = selectedCategory.getId();
                        String user = income.getUser();
                        Log.d("DEBUG", "category_id = " + categoryId);
                        apiService.updateIncome(
                                income.getId(),
                                newAmount,
                                newNote,
                                newDate,
                                categoryId,
                                user
                        ).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    String body = response.body().string();
                                    Log.d(TAG, "Response raw: " + body);

                                    JSONObject obj = new JSONObject(body);
                                    if (obj.getString("status").equals("success")) {
                                        Toast.makeText(IncomeActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                        loadIncomeList(user);
                                    } else {
                                        Toast.makeText(IncomeActivity.this, "Cập nhật thất bại: " + obj.getString("message"), Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e) {
                                    Log.e(TAG, "Lỗi parse JSON: " + e.getMessage(), e);
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.e(TAG, "Lỗi kết nối khi cập nhật", t);
                                Toast.makeText(IncomeActivity.this, "Lỗi kết nối khi cập nhật", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } catch (Exception e) {
                        Log.e(TAG, "Lỗi khi xử lý chỉnh sửa", e);
                        Toast.makeText(this, "Lỗi xử lý dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Huỷ", null)
                .show();
    }
    private void loadIncomeList(String currentUser) {
        Log.d(TAG, "loadIncomeList: Gọi API lấy dữ liệu cho user = " + currentUser);

        apiService.getIncomeByUser(currentUser).enqueue(new Callback<List<Income>>() {
            @Override
            public void onResponse(Call<List<Income>> call, Response<List<Income>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "loadIncomeList: Lấy thành công " + response.body().size() + " khoản thu");
                    incomeList.clear();
                    incomeList.addAll(response.body());
                    incomeAdapter.notifyDataSetChanged();
                } else {
                    Log.e(TAG, "loadIncomeList: Lỗi response: " + response.code());
                    Toast.makeText(IncomeActivity.this, "Không có dữ liệu hoặc lỗi phản hồi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Income>> call, Throwable t) {
                Log.e(TAG, "loadIncomeList: Lỗi kết nối", t);
                Toast.makeText(IncomeActivity.this, "Lỗi kết nối API", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveIncome() {
        String amountStr = etAmount.getText().toString().trim();
        String note = etNote.getText().toString().trim();

        if (TextUtils.isEmpty(amountStr)) {
            Toast.makeText(this, "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedCategory == null) {
            Toast.makeText(this, "Vui lòng chọn danh mục", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Số tiền không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        int categoryId = selectedCategory.getId();  // Lấy ID từ danh mục đã chọn

        Log.d(TAG, "saveIncome: Gọi API insert với amount=" + amount + ", note=" + note + ", date=" + currentDate + ", category_id=" + categoryId);

        apiService.insertIncome(amount, note, currentDate, categoryId, currentUser)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d(TAG, "insertIncome onResponse: code=" + response.code() + ", body=" + response.body());
                        Log.d(TAG, "insertIncome body: " + response.body());
                        Log.d(TAG, "insertIncome message: " + response.message());
                        Log.d(TAG, "insertIncome raw: " + response.raw());
                        Log.d(TAG, "Ngày giờ hiện tại: " + currentDate);

                        if (response.isSuccessful() && "success".equalsIgnoreCase(response.body())) {
                            Toast.makeText(IncomeActivity.this, "Lưu khoản thu thành công", Toast.LENGTH_SHORT).show();
                            etAmount.setText("");
                            etNote.setText("");
                            loadIncomeList(currentUser);
                        } else {
                            Log.e(TAG, "saveIncome: Lỗi khi lưu khoản thu, code = " + response.code());
                            Toast.makeText(IncomeActivity.this, "Lỗi khi lưu khoản thu", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e(TAG, "insertIncome onFailure", t);
                        Toast.makeText(IncomeActivity.this, "Lỗi kết nối khi lưu khoản thu", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void Back() {
        Log.d(TAG, "Back: Quay về MainActivity_Final");
        Intent intent = new Intent(IncomeActivity.this, MainActivity_Final.class);
        startActivity(intent);
        finish();
    }

}
