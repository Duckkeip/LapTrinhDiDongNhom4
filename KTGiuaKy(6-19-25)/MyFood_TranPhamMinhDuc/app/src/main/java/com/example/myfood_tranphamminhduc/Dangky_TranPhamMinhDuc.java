package com.example.myfood_tranphamminhduc;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfood_tranphamminhduc.model.User_TranPhamMinhDuc;

public class Dangky_TranPhamMinhDuc extends AppCompatActivity {
    private EditText edtUser, edtPass, edtRePass;
    private Button btnDangKi;
    private Food_TranPhamMinhDuc.User_TranPhamMinhDuc User_TranPhamMinhDuc;

    private  TextView daCoTK;

    private VideoView videobg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dangky_tpmd);

        User_TranPhamMinhDuc = new Food_TranPhamMinhDuc.User_TranPhamMinhDuc(this);

        edtUser = findViewById(R.id.tk);
        edtPass = findViewById(R.id.mk);
        edtRePass = findViewById(R.id.remk);
        btnDangKi = findViewById(R.id.dk);
        daCoTK = findViewById(R.id.dacotk);

        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUser.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                String repass = edtRePass.getText().toString().trim();

                if (user.isEmpty() || pass.isEmpty() || repass.isEmpty()) {
                    Toast.makeText(Dangky_TranPhamMinhDuc.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!pass.equals(repass)) {
                    Toast.makeText(Dangky_TranPhamMinhDuc.this, "Mật khẩu nhập lại không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (User_TranPhamMinhDuc.checkUser(user)) {
                    Toast.makeText(Dangky_TranPhamMinhDuc.this, "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }

                User_TranPhamMinhDuc userObj = new User_TranPhamMinhDuc();
                userObj.setUser(user);
                userObj.setPass(pass);

                long result = User_TranPhamMinhDuc.insert(userObj);
                if (result > 0) {
                    Toast.makeText(Dangky_TranPhamMinhDuc.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Dangky_TranPhamMinhDuc.this, DangNhap_TranPhamMinhDuc.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Dangky_TranPhamMinhDuc.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        daCoTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang activity đăng nhập
                Intent intent = new Intent(Dangky_TranPhamMinhDuc.this, DangNhap_TranPhamMinhDuc.class);
                startActivity(intent);
            }
        });
    }
}
