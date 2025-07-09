package com.example.sqlite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class DangnhapActivity extends AppCompatActivity {
    private EditText edtUser, edtPass;
    private Button btnDangNhap;
    private VideoView videobg;
    private TextView btnDangKy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_dangnhap);

        String url = "http://192.168.1.149/lms/log/login.php";// Đổi yourfolder thành đúng tên thư mục PHP

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                edtUser = findViewById(R.id.edtUser);
                edtPass = findViewById(R.id.edtPass);
                btnDangNhap = findViewById(R.id.btnDangNhap);
                btnDangKy = findViewById(R.id.btnDangKy);
                videobg = findViewById(R.id.videobg);

                //videobackground trong raw
                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.videobg);
                videobg.setVideoURI(uri);
                videobg.start();
                videobg.setOnPreparedListener(mp -> {
                    mp.setLooping(true); // lặp lại video
                });



                btnDangNhap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String user = edtUser.getText().toString().trim();
                        String pass = edtPass.getText().toString().trim();

                        if (user.isEmpty() || pass.isEmpty()) {
                            Toast.makeText(DangnhapActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                response -> {
                                    if (response.equals("user")) {
                                        Toast.makeText(DangnhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                                        // Lưu tài khoản đăng nhập
                                        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.putString("username", user);
                                        editor.putString("password",pass);

                                        editor.apply();

                                        // Chuyển màn hình
                                        Intent intent = new Intent(DangnhapActivity.this, MainActivity_Final.class);

                                        startActivity(intent);
                                        finish();
                                    } else if (response.equals("failure")) {
                                        Toast.makeText(DangnhapActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                                    } else if (response.equals("missing_fields")) {
                                        Toast.makeText(DangnhapActivity.this, "Thiếu thông tin đầu vào", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(DangnhapActivity.this, "Lỗi không xác định: " + response, Toast.LENGTH_SHORT).show();
                                    }
                                },
                                error -> {
                                    error.printStackTrace();

                                    String message = "Lỗi không xác định";
                                    if (error.networkResponse == null) {
                                        message = "Không thể kết nối server. Kiểm tra mạng hoặc IP máy chủ.";
                                    } else {
                                        message = "Lỗi HTTP: " + error.networkResponse.statusCode;
                                    }

                                    Toast.makeText(DangnhapActivity.this, message, Toast.LENGTH_LONG).show();
                                }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("username", user);   // Đổi từ login → username
                                params.put("password", pass);
                                return params;
                            }
                        };

                        queue.add(stringRequest);
                    }
                });

                btnDangKy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Chuyển sang màn hình đăng ký
                        Intent intent = new Intent(DangnhapActivity.this, DangkyActivity.class);
                        startActivity(intent);
                    }
                });
    }
}
