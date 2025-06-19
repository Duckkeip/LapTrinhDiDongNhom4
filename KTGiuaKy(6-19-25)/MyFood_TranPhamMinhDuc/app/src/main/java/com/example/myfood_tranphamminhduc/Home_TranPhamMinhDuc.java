package com.example.myfood_tranphamminhduc;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfood_tranphamminhduc.model.Restaurant_TranPhamMinhDuc;

import java.util.List;

import Food_TranPhamMinhDuc.User_TranPhamMinhDuc;
import adapter.RestaurantAdapter_TranPhamMinhDuc;

public class Home_TranPhamMinhDuc extends AppCompatActivity {

    RecyclerView recyclerView;
    RestaurantAdapter_TranPhamMinhDuc adapter;
    User_TranPhamMinhDuc dbAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tpmd);

        recyclerView = findViewById(R.id.recyclerRestaurant);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbAccess = new User_TranPhamMinhDuc(this);
        List<Restaurant_TranPhamMinhDuc> list = dbAccess.getAllRestaurants();

        adapter = new RestaurantAdapter_TranPhamMinhDuc(this, list);
        recyclerView.setAdapter(adapter);
    }
}
