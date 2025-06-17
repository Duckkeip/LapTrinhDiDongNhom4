package com.example.sqlite.func;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlite.MainActivity;
import com.example.sqlite.R;

public class BudgetActivity extends AppCompatActivity {
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget); // tạo file layout tương ứng

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
    }
}
