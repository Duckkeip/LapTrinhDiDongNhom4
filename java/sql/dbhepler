package com.example.sqlite.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "Demo6";
    public static final int DB_VERSION = 5;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //nhanvien
        String sqlNhanvien = "CREATE TABLE nhanvien(" +
                "id text primary key, " +
                "name text not null, " +
                " salary integer not null )";
        db.execSQL(sqlNhanvien);
        //users
        String sqlUser = "CREATE TABLE users(" +
                "username TEXT PRIMARY KEY, " +
                "password TEXT NOT NULL )";
        db.execSQL(sqlUser);
        //category
        String sqlLoaichitieu = "CREATE TABLE loaichitieu (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "type TEXT NOT NULL CHECK(type IN ('income','expense')))";
        db.execSQL(sqlLoaichitieu);

        // Thêm vào sau khi tạo bảng loaichitieu
        db.execSQL("INSERT INTO loaichitieu (name, type) VALUES ('Lương', 'income')");
        db.execSQL("INSERT INTO loaichitieu (name, type) VALUES ('Thưởng', 'income')");
        db.execSQL("INSERT INTO loaichitieu (name, type) VALUES ('Ăn uống', 'expense')");
        db.execSQL("INSERT INTO loaichitieu (name, type) VALUES ('Giải trí', 'expense')");


        //transaction
        String sqlGiaodich = "CREATE TABLE giaodich (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "amount REAL NOT NULL, " +
                "note TEXT, " +
                "date TEXT NOT NULL, " +
                "category_id INTEGER NOT NULL, " +
                "user TEXT NOT NULL, " +
                "FOREIGN KEY (category_id) REFERENCES loaichitieu(id), " +
                "FOREIGN KEY (user) REFERENCES user(user))";
        db.execSQL(sqlGiaodich);
        //wallet
        String sqlVi = "CREATE TABLE vi (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "balance REAL DEFAULT 0, " +
                "user TEXT NOT NULL, " +
                "FOREIGN KEY (user) REFERENCES user(user))";
        db.execSQL(sqlVi);
        //budget
        String sqlNgansach = ("CREATE TABLE ngansach (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "category_id INTEGER NOT NULL, " +
                "amount REAL NOT NULL, " +
                "start_date TEXT NOT NULL, " +
                "end_date TEXT NOT NULL, " +
                "user TEXT NOT NULL, " +
                "FOREIGN KEY (category_id) REFERENCES loaichitieu(id), " +
                "FOREIGN KEY (user) REFERENCES user(user))");
        db.execSQL(sqlNgansach);
        //
        String sqlTag = ("CREATE TABLE tag (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL)");
        db.execSQL(sqlTag);
        //
        String sqlGiaodich_tag = ("CREATE TABLE giaodich_tag (" +
                "transaction_id INTEGER, " +
                "tag_id INTEGER, " +
                "PRIMARY KEY (transaction_id, tag_id), " +
                "FOREIGN KEY (transaction_id) REFERENCES giaodich(id), " +
                "FOREIGN KEY (tag_id) REFERENCES tag(id))");
        db.execSQL(sqlGiaodich_tag);





        //here

    }



    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS nhanvien");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS loaichitieu");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS giaodich");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS vi");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS ngansach");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tag");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS giaodich_tag");

        onCreate(sqLiteDatabase);

    }


}
