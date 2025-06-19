package Food_TranPhamMinhDuc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myfood_tranphamminhduc.model.Restaurant_TranPhamMinhDuc;

import java.util.ArrayList;
import java.util.List;

public class User_TranPhamMinhDuc {

    private SQLiteDatabase db;

    public User_TranPhamMinhDuc(Context context) {
        DB_TranPhamMinhDuc helper = new DB_TranPhamMinhDuc(context);
        db = helper.getWritableDatabase();
    }

    public long insert(com.example.myfood_tranphamminhduc.model.User_TranPhamMinhDuc user) {
        ContentValues values = new ContentValues();
        values.put("username", user.getUser());
        values.put("password", user.getPass());
        return db.insert("User", null, values);
    }

    public boolean checkUser(String userName) {
        String sql = "SELECT * FROM User WHERE username = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{userName});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkUserPass(String user, String pass) {
        String sql = "SELECT * FROM User WHERE username = ? AND password = ?";
        List<com.example.myfood_tranphamminhduc.model.User_TranPhamMinhDuc> list = get(sql, user, pass);
        return list.size() > 0;
    }

    private List<com.example.myfood_tranphamminhduc.model.User_TranPhamMinhDuc> get(String sql, String... args) {
        List<com.example.myfood_tranphamminhduc.model.User_TranPhamMinhDuc> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, args);
        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            list.add(new com.example.myfood_tranphamminhduc.model.User_TranPhamMinhDuc(username, password));
        }
        cursor.close();
        return list;
    }

    public com.example.myfood_tranphamminhduc.model.User_TranPhamMinhDuc getUserByUsername(String username) {
        String sql = "SELECT * FROM User WHERE username = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{username});
        if (cursor.moveToFirst()) {
            String user = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String pass = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            cursor.close();
            return new com.example.myfood_tranphamminhduc.model.User_TranPhamMinhDuc(user, pass);
        }
        cursor.close();
        return null;
    }

    public long update(com.example.myfood_tranphamminhduc.model.User_TranPhamMinhDuc user) {
        ContentValues values = new ContentValues();
        values.put("password", user.getPass());
        return db.update("User", values, "username = ?", new String[]{user.getUser()});
    }

    public List<Restaurant_TranPhamMinhDuc> getAllRestaurants() {
        List<Restaurant_TranPhamMinhDuc> list = new ArrayList<>();
        String sql = "SELECT * FROM Restaurant";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("ResID"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("Address"));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow("Phone"));
            String image = cursor.getString(cursor.getColumnIndexOrThrow("Image"));

            list.add(new Restaurant_TranPhamMinhDuc(id, name, address, phone, image));
        }
        cursor.close();
        return list;
    }
}
