package com.example.sqlite.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlite.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private SQLiteDatabase db;

    public UserDao(Context context) {
        DBHelper helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public long insert(User user) {
        ContentValues values = new ContentValues();
        values.put("username", user.getUser());
        values.put("password", user.getPass());
        return db.insert("users", null, values);
    }

    // Thêm các hàm khác nếu cần, ví dụ kiểm tra đăng nhập
    public boolean checkUser(String userName) {
        String sql = "SELECT * FROM users WHERE username = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{userName});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    public boolean checkUserPass(String user, String pass) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        List<User> list = get(sql, user, pass);
        return list.size() > 0;
    }
    private List<User> get(String sql, String... args) {
        List<User> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, args);
        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            list.add(new User(username, password));
        }
        cursor.close();
        return list;
    }
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{username});
        if (cursor.moveToFirst()) {
            String user = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String pass = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            cursor.close();
            return new User(user, pass);
        }
        cursor.close();
        return null;
    }
    //update password
    public long update(User user) {
        ContentValues values = new ContentValues();
        values.put("password", user.getPass());
        return db.update("users", values, "username = ?", new String[]{user.getUser()});
    }
}
