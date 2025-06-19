package com.example.myfood_tranphamminhduc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import Food_TranPhamMinhDuc.User_TranPhamMinhDuc;

public class DangNhap_TranPhamMinhDuc extends AppCompatActivity {
    private EditText edtUser, edtPass;
    private Button btnDangNhap;
    private VideoView videobg;
    private TextView btnDangKy;
    private User_TranPhamMinhDuc User_TranPhamMinhDuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangnhap_tpmd);

        User_TranPhamMinhDuc = new User_TranPhamMinhDuc(this);

        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKy = findViewById(R.id.btnDangKy);


        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = edtUser.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();

                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(DangNhap_TranPhamMinhDuc.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean checkLogin = User_TranPhamMinhDuc.checkUserPass(user, pass);
                if (checkLogin) {
                    Toast.makeText(DangNhap_TranPhamMinhDuc.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                    SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("username", edtUser.getText().toString());
                    editor.apply();


                    // Chuyển sang màn hình chính (ví dụ MainActivity)
                    Intent intent = new Intent(DangNhap_TranPhamMinhDuc.this, Home_TranPhamMinhDuc.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(DangNhap_TranPhamMinhDuc.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Chuyển sang màn hình đăng ký
                Intent intent = new Intent(DangNhap_TranPhamMinhDuc.this, Dangky_TranPhamMinhDuc.class);
                startActivity(intent);
            }
        });
    }
}
