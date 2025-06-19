package Food_TranPhamMinhDuc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myfood_tranphamminhduc.model.Food_TranPhamMinhDuc;

import java.util.ArrayList;
import java.util.List;

public class Fooda_TranPhamMinhDuc {

    private SQLiteDatabase db;

    public Fooda_TranPhamMinhDuc(Context context) {
        DB_TranPhamMinhDuc dbHelper = new DB_TranPhamMinhDuc(context);
        db = dbHelper.getReadableDatabase();
    }

    public List<Food_TranPhamMinhDuc> getFoodsByRestaurant(int resId) {
        List<Food_TranPhamMinhDuc> list = new ArrayList<>();
        String sql = "SELECT * FROM Food WHERE ResID = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(resId)});
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("FoodID"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("Name"));
            String type = cursor.getString(cursor.getColumnIndexOrThrow("Type"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("Description"));
            String image = cursor.getString(cursor.getColumnIndexOrThrow("Image"));
            int restaurantId = cursor.getInt(cursor.getColumnIndexOrThrow("ResID"));

            list.add(new Food_TranPhamMinhDuc(id, name, type, description, image, restaurantId));
        }
        cursor.close();
        return list;
    }
}
