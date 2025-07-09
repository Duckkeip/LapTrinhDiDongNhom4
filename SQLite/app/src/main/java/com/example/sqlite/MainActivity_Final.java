package com.example.sqlite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlite.func.BackupActivity;
import com.example.sqlite.func.BudgetActivity;
import com.example.sqlite.func.ExpenseActivity;
import com.example.sqlite.func.IncomeActivity;
import com.example.sqlite.func.ReportActivity;
import com.example.sqlite.func.SearchActivity;
import com.example.sqlite.func.TagActivity;
import com.example.sqlite.func.WalletActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity_Final extends AppCompatActivity {
    LinearLayout btnIncome, btnExpense, btnReport, btnWallet, btnBudget,
            btnTagNote, btnSearch, btnBackup, btnLogout;
    Toolbar toolbar;
    ImageView useravatar;
    private String username, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalmain);

        // 1. Kiểm tra login
        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        String currentUser = prefs.getString("username", "");
        username = prefs.getString("username", "");
        pass = prefs.getString("password", "");

        if (currentUser == null || currentUser.isEmpty()) {
            // Chưa đăng nhập -> về lại màn đăng nhập
            Intent intent = new Intent(this, DangnhapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return;
        }

        // 2. Lưu vào biến toàn cục



        // 3. Giao diện
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(username);

        useravatar = findViewById(R.id.useravatar);
        useravatar.setOnClickListener(v -> showUserMenu(v));

        // 4. Gán nút
        btnIncome = findViewById(R.id.btnIncome);
        btnExpense = findViewById(R.id.btnExpense);
        btnReport = findViewById(R.id.btnReport);
        btnWallet = findViewById(R.id.btnWallet);
        btnBudget = findViewById(R.id.btnBudget);
        btnTagNote = findViewById(R.id.btnTagNote);
        btnSearch = findViewById(R.id.btnSearch);
        btnBackup = findViewById(R.id.btnBackup);
        btnLogout = findViewById(R.id.btnLogout);

        btnIncome.setOnClickListener(v -> startActivity(new Intent(this, IncomeActivity.class)));
        btnExpense.setOnClickListener(v -> startActivity(new Intent(this, ExpenseActivity.class)));
        btnReport.setOnClickListener(v -> startActivity(new Intent(this, ReportActivity.class)));
        btnWallet.setOnClickListener(v -> startActivity(new Intent(this, WalletActivity.class)));
        btnBudget.setOnClickListener(v -> startActivity(new Intent(this, BudgetActivity.class)));
        btnTagNote.setOnClickListener(v -> startActivity(new Intent(this, TagActivity.class)));
        btnSearch.setOnClickListener(v -> startActivity(new Intent(this, SearchActivity.class)));
        btnBackup.setOnClickListener(v -> startActivity(new Intent(this, BackupActivity.class)));

        btnLogout.setOnClickListener(v -> logout());
    }

    private void showUserMenu(View anchor) {
        PopupMenu popupMenu = new PopupMenu(MainActivity_Final.this, anchor);
        popupMenu.getMenuInflater().inflate(R.menu.user_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_user_info) {

                showUserInfo();
            }
            if (item.getItemId() == R.id.menu_settings) {

                changePassword();
            }
            if (item.getItemId() == R.id.btnLogout) {

                logout();
            }

            return false;
        });
        popupMenu.show();
    }
    private void showUserInfo() {
        String info = "Tên đăng nhập: " + username + "\nMật khẩu: " + pass;
        new AlertDialog.Builder(this)
                .setTitle("Thông tin người dùng")
                .setMessage(info)
                .setPositiveButton("OK", null)
                .show();
    }

    private void changePassword() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_change_password, null);
        EditText edtCurrentPass = dialogView.findViewById(R.id.edtCurrentPassword);
        EditText edtNewPass = dialogView.findViewById(R.id.edtNewPassword);
        EditText edtConfirmPass = dialogView.findViewById(R.id.edtConfirmNewPassword);

        new AlertDialog.Builder(this)
                .setTitle("Đổi mật khẩu")
                .setView(dialogView)
                .setPositiveButton("Đổi", (dialog, which) -> {
                    String currentPass = edtCurrentPass.getText().toString().trim();
                    String newPass = edtNewPass.getText().toString().trim();
                    String confirmPass = edtConfirmPass.getText().toString().trim();

                    if (currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                        Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!newPass.equals(confirmPass)) {
                        Toast.makeText(this, "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    new Thread(() -> {
                        try {
                            URL url = new URL("http://192.168.1.149/lms/mainactivity/change_password.php");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setDoOutput(true);

                            String postData = "username=" + URLEncoder.encode(username, "UTF-8")
                                    + "&currentpass=" + URLEncoder.encode(currentPass, "UTF-8")
                                    + "&newpass=" + URLEncoder.encode(newPass, "UTF-8");

                            OutputStream os = conn.getOutputStream();
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                            writer.write(postData);
                            writer.flush();
                            writer.close();
                            os.close();

                            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            String response = reader.readLine();
                            reader.close();

                            runOnUiThread(() -> {
                                switch (response) {
                                    case "OK":
                                        Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "WRONG_PASS":
                                        Toast.makeText(this, "Mật khẩu hiện tại không đúng", Toast.LENGTH_SHORT).show();
                                        break;
                                    case "FAIL_UPDATE":
                                        Toast.makeText(this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                                        break;

                                    default:
                                        Toast.makeText(this, "Lỗi không xác định: " + response, Toast.LENGTH_LONG).show();
                                        Log.e("ChangePass", "Phản hồi từ server: " + response);
                                        break;
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(() ->
                                    Toast.makeText(this, "Lỗi kết nối server", Toast.LENGTH_SHORT).show());
                        }
                    }).start();

                })
                .setNegativeButton("Hủy", null)
                .show();
    }


    private void logout() {
        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        prefs.edit().clear().apply();

        Intent intent = new Intent(this, DangnhapActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}

