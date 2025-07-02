package com.example.sqlite.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sqlite.adapter.EmployeeAdapter;
import com.example.sqlite.model.Employee;
import com.example.sqlite.model.Income;
import com.example.sqlite.sqlite.DBHelper;
import com.example.sqlite.sqlite.EmployeeDao;

import java.util.ArrayList;
import java.util.List;

public class IncomeDao {
    private SQLiteDatabase db;

    public IncomeDao(Context context) {
        DBHelper helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public long insertIncome(Income income) {
        ContentValues values = new ContentValues();
        values.put("amount", income.getAmount());
        values.put("note", income.getNote());
        values.put("date", income.getDate());
        values.put("category_id", income.getCategoryId());
        values.put("user", income.getUser());
        return db.insert("giaodich", null, values);
    }

    public List<Income> getAllIncomeByUser(String user) {
        String sql = "SELECT gd.id AS gd_id, gd.amount, gd.note, gd.date, gd.category_id, gd.user " +
                "FROM giaodich gd " +
                "INNER JOIN loaichitieu lc ON gd.category_id = lc.id " +
                "WHERE lc.type = 'income' AND gd.user = ?";
        return get(sql, user);
    }

    private List<Income> get(String sql, String... args) {
        List<Income> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, args);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("gd_id"));
            double amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"));
            String note = cursor.getString(cursor.getColumnIndexOrThrow("note"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            int categoryId = cursor.getInt(cursor.getColumnIndexOrThrow("category_id"));
            String user = cursor.getString(cursor.getColumnIndexOrThrow("user"));



            list.add(new Income(id, amount, note, date, categoryId, user));
        }
        cursor.close();
        return list;
    }
    public void updateIncome(Income income) {
            ContentValues values = new ContentValues();
        values.put("amount", income.getAmount());
        values.put("note", income.getNote());

        db.update("giaodich", values, "id = ? ", new String[]{
                String.valueOf(income.getId())
        });

    }
    public void deleteIncome(int id) {
        db.delete("giaodich", "id = ?", new String[]{String.valueOf(id)});
    }
}
