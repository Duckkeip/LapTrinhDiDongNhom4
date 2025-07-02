package com.example.btthay;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


public class MainActivity extends AppCompatActivity {

    TextView txtResult;
    Button btnCallApi;
    ImageView imgFromApi;


    RecyclerView recyclerView;
    ImageAdapter adapter;
    List<String> imageUrls = new ArrayList<>();

    RecyclerView recyclerViewUsers;
    UserAdapter userAdapter;
    List<User> userList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = findViewById(R.id.txtResult);
        btnCallApi = findViewById(R.id.btnCallApi);
        imgFromApi = findViewById(R.id.imgFromApi); // ánh xạ ImageView

        btnCallApi.setOnClickListener(v -> callApi());


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ImageAdapter(this, imageUrls);
        recyclerView.setAdapter(adapter);

        loadImagesFromApi();



        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(userList);
        recyclerViewUsers.setAdapter(userAdapter);

        loadUsersFromApi(); // gọi sau loadImagesFromApi()
    }
    void loadUsersFromApi() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/users")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonData = response.body().string();
                    Gson gson = new Gson();
                    Type userListType = new TypeToken<List<User>>() {}.getType();
                    List<User> users = gson.fromJson(jsonData, userListType);

                    runOnUiThread(() -> {
                        userList.clear();
                        userList.addAll(users);
                        userAdapter.notifyDataSetChanged();
                    });
                }
            }
        });
    }
    void loadImagesFromApi() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://dog.ceo/api/breeds/image/random/10")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace(); // Có thể Toast lỗi ở đây
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String jsonData = response.body().string();
                        JSONObject jsonObject = new JSONObject(jsonData);
                        JSONArray images = jsonObject.getJSONArray("message");

                        imageUrls.clear();
                        for (int i = 0; i < images.length(); i++) {
                            imageUrls.add(images.getString(i));
                        }

                        runOnUiThread(() -> adapter.notifyDataSetChanged());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    void callApi() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts/1") // Thay thế nếu có API chứa ảnh
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> txtResult.setText("Lỗi: " + e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(json);

                        // Giả sử API có trường "imageUrl"
                        String imageUrl = "https://i.imgur.com/DvpvklR.png"; // thay bằng jsonObject.getString("imageUrl");

                        String userId = "• userId: " + jsonObject.getInt("userId");
                        String id = "• id: " + jsonObject.getInt("id");
                        String title = "• title: Giới thiệu về Networking APIs trong Android";
                        String body = "• body: Bài viết này minh họa cách sử dụng HTTP để truy xuất dữ liệu từ Internet bằng thư viện OkHttp.";

                        String displayText = userId + "\n" + id + "\n" + title + "\n" + body;

                        runOnUiThread(() -> {
                            txtResult.setText(displayText);
                            Glide.with(MainActivity.this).load(imageUrl).into(imgFromApi);
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> txtResult.setText("Lỗi xử lý JSON: " + e.getMessage()));
                    }
                }
            }
        });

    }
}
