package com.example.intentextras;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    ImageView detailImageView;
    TextView detailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailImageView = findViewById(R.id.detailImageView);
        detailTextView = findViewById(R.id.detailTextView);

        String foodName        = getIntent().getStringExtra("foodName");
        int foodImage          = getIntent().getIntExtra("foodImage", 0);
        String foodDescription = getIntent().getStringExtra("foodDescription");
        double foodPrice       = getIntent().getDoubleExtra("FoodPrice", 0);

        detailImageView.setImageResource(foodImage);

        String detailText = "Tên món ăn: " + foodName +
                "\nMô tả: " + foodDescription +
                "\nGiá: " + foodPrice + " VND";

        detailTextView.setText(detailText);
    }
}
