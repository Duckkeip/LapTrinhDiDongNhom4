package com.example.sqlite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
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

import com.example.sqlite.adapter.IncomeAdapter;
import com.example.sqlite.model.Income;
import com.example.sqlite.sqlite.IncomeDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class IncomeActivity extends AppCompatActivity {

    private EditText etAmount, etNote;
    private Button btnSaveIncome,btnBackMainfinal;
    private RecyclerView recyclerViewIncome;
    private IncomeAdapter incomeAdapter;
    private IncomeDao incomeDao;
    private List<Income> incomeList;

    private Spinner spinnerCategory;

    private String currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);


        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        currentUser = prefs.getString("username", "");


        etAmount = findViewById(R.id.etAmount);
        etNote = findViewById(R.id.etNote);
        btnSaveIncome = findViewById(R.id.btnSaveIncome);
        btnBackMainfinal = findViewById(R.id.btnBack);


        recyclerViewIncome = findViewById(R.id.recyclerViewIncome);
        recyclerViewIncome.setLayoutManager(new LinearLayoutManager(this));

        incomeDao = new IncomeDao(this);

        btnSaveIncome.setOnClickListener(v -> saveIncome());
        btnBackMainfinal.setOnClickListener(v -> Back());

        //test
        incomeList = new ArrayList<>();
        incomeAdapter = new IncomeAdapter(incomeList, new IncomeAdapter.OnItemActionListener() {
            @Override
            public void onDelete(Income income) {
                incomeDao.deleteIncome(income.getId());
                incomeList.remove(income);
                incomeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onEdit(Income income) {
                showEditDialog(income);
            }
        });
        recyclerViewIncome.setAdapter(incomeAdapter);
        loadIncomeList(currentUser);
    }
    public void loadIncomeList(String currentUser) {
        try {
            List<Income> data = incomeDao.getAllIncomeByUser(currentUser);
            incomeList.clear();
            if (data != null) {
                incomeList.addAll(data);
            }
            incomeAdapter.notifyDataSetChanged();

            Log.d("IncomeActivity", "Khoản thu lấy về: " + incomeList.size());
            for (Income i : incomeList) {
                Log.d("IncomeItem", i.getAmount() + " | " + i.getNote() + " | " + i.getDate());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi tải danh sách khoản thu", Toast.LENGTH_SHORT).show();
        }
    }
    private void Back(){
        Intent intent = new Intent(IncomeActivity.this, MainActivity_Final.class);
        startActivity(intent);
    }
    private void saveIncome() {
        String amountStr = etAmount.getText().toString().trim();
        String note = etNote.getText().toString().trim();

        if (TextUtils.isEmpty(amountStr)) {
            Toast.makeText(this, "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Số tiền không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        Income income = new Income();
        income.setAmount(amount);
        income.setNote(note);
        income.setUser(currentUser);
        String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
        income.setDate(currentDate);// hoặc format ngày theo ý bạn
        income.setCategoryId(1);


        long id = incomeDao.insertIncome(income);
        if (id > 0) {
            Toast.makeText(this, "Lưu khoản thu thành công", Toast.LENGTH_SHORT).show();
            etAmount.setText("");
            etNote.setText("");
            loadIncomeList(currentUser); // tải lại danh sách sau khi thêm mới
        } else {
            Toast.makeText(this, "Lỗi khi lưu khoản thu", Toast.LENGTH_SHORT).show();
        }
    }
    private void showEditDialog(Income income) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_income, null);
        EditText etAmount = view.findViewById(R.id.etEditAmount);
        EditText etNote = view.findViewById(R.id.etEditNote);

        etAmount.setText(String.valueOf(income.getAmount()));
        etNote.setText(income.getNote());

        builder.setView(view)
                .setTitle("Chỉnh sửa khoản thu")
                .setPositiveButton("Lưu", (dialog, which) -> {
                    try {
                        Log.d("EditIncome", "Bắt đầu lưu chỉnh sửa");

                        String amountStr = etAmount.getText().toString().trim();
                        String newNote = etNote.getText().toString().trim();

                        Log.d("EditIncome", "amountStr = " + amountStr);
                        Log.d("EditIncome", "newNote = " + newNote);

                        if (amountStr.isEmpty()) {
                            Toast.makeText(this, "Vui lòng nhập số tiền", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        float newAmount = Float.parseFloat(amountStr);

                        income.setAmount(newAmount);
                        income.setNote(newNote);

                        incomeDao.updateIncome(income);
                        incomeAdapter.notifyDataSetChanged();

                        Log.d("EditIncome", "Đã cập nhật thành công income ID = " + income.getId());
                    } catch (Exception e) {
                        Log.e("EditIncome", "Lỗi khi lưu chỉnh sửa: " + e.getMessage(), e);
                        Toast.makeText(this, "Có lỗi xảy ra khi lưu", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Huỷ", null)
                .show();
    }

}