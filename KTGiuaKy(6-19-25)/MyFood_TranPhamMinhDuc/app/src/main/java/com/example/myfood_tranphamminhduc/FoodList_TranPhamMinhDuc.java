package com.example.myfood_tranphamminhduc;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfood_tranphamminhduc.model.Food_TranPhamMinhDuc;

import java.util.List;

import Food_TranPhamMinhDuc.Fooda_TranPhamMinhDuc;
import adapter.FoodAdapter_TranPhamMinhDuc;

public class FoodList_TranPhamMinhDuc extends AppCompatActivity {

    RecyclerView recyclerView;
    FoodAdapter_TranPhamMinhDuc adapter;
    Fooda_TranPhamMinhDuc fooda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list_tpmd);

        recyclerView = findViewById(R.id.recyclerFood);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int resId = getIntent().getIntExtra("res_id", -1);
        if (resId == -1) {
            Toast.makeText(this, "Không tìm thấy nhà hàng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        fooda = new Fooda_TranPhamMinhDuc(this);
        List<Food_TranPhamMinhDuc> foodList = fooda.getFoodsByRestaurant(resId);

        adapter = new FoodAdapter_TranPhamMinhDuc(this, foodList);
        recyclerView.setAdapter(adapter);
    }
}
