package com.example.sqlite;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class DangkyActivity extends AppCompatActivity {
    private EditText edtUser, edtPass, edtRePass;
    private Button btnDangKi;
    private TextView daCoTK;
    private VideoView videobg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);

        edtUser = findViewById(R.id.tk);
        edtPass = findViewById(R.id.mk);
        edtRePass = findViewById(R.id.remk);
        btnDangKi = findViewById(R.id.dk);
        daCoTK = findViewById(R.id.dacotk);
        videobg = findViewById(R.id.videobg);

        // video background
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.videobg);
        videobg.setVideoURI(uri);
        videobg.start();
        videobg.setOnPreparedListener(mp -> mp.setLooping(true));

        btnDangKi.setOnClickListener(v -> {
            String user = edtUser.getText().toString().trim();
            String pass = edtPass.getText().toString().trim();
            String repass = edtRePass.getText().toString().trim();

            if (pass.length() < 6) {
                Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
                return;
            }
            if (user.isEmpty() || pass.isEmpty() || repass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!pass.equals(repass)) {
                Toast.makeText(this, "Mật khẩu nhập lại không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            // Gửi dữ liệu đến PHP để đăng ký
            String url = "http://192.168.1.149/lms/log/register.php";

            StringRequest request = new StringRequest(Request.Method.POST, url,
                    response -> {
                        switch (response.trim()) {
                            case "success":
                                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(this, DangnhapActivity.class));
                                finish();
                                break;
                            case "exists":
                                Toast.makeText(this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                                break;
                            case "missing_fields":
                                Toast.makeText(this, "Thiếu thông tin gửi lên server", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(this, "Lỗi đăng ký: " + response, Toast.LENGTH_SHORT).show();
                                break;
                        }
                    },
                    error -> {
                        error.printStackTrace(); // In lỗi chi tiết ra Logcat
                        Toast.makeText(this, "Lỗi: " + error.toString(), Toast.LENGTH_LONG).show();
                    }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> data = new HashMap<>();
                    data.put("username", user);
                    data.put("password", pass);
                    return data;
                }
            };

            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        });

        daCoTK.setOnClickListener(v -> {
            startActivity(new Intent(DangkyActivity.this, DangnhapActivity.class));
        });
    }
}
