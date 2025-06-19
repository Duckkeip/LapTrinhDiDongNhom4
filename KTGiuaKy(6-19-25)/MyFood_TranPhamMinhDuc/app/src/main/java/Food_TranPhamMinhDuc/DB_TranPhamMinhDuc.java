package Food_TranPhamMinhDuc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DB_TranPhamMinhDuc extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "FoodApp.db";
    private static final int DATABASE_VERSION = 4;

    public DB_TranPhamMinhDuc(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String User = "CREATE TABLE User (" +
                "UserID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT," +
                "Gender TEXT," +
                "Date_of_birth TEXT," +
                "Phone TEXT," +
                "username TEXT," +
                "password TEXT" +
                ");";
        db.execSQL(User);

        String Restaurant = "CREATE TABLE restaurant (" +
                "ResID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT," +
                "Address TEXT," +
                "Phone TEXT," +
                "Image TEXT" +
                ");";
        db.execSQL(Restaurant);

        String Food = "CREATE TABLE food (" +
                "FoodID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT," +
                "Type TEXT," +
                "Description TEXT," +
                "Image TEXT," +
                "ResID INTEGER," +
                "FOREIGN KEY (ResID) REFERENCES Restaurant(ResID)" +
                ");";
        db.execSQL(Food);

        String Order = "CREATE TABLE 'order' (" +
                "OrderID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Address TEXT," +
                "Date_order TEXT," +
                "Total_value REAL," +
                "Status TEXT," +
                "UserID INTEGER," +
                "FOREIGN KEY (UserID) REFERENCES User(UserID)" +
                ");";
        db.execSQL(Order);

        String Order_Detail = "CREATE TABLE orderDetail (" +
                "OrderID INTEGER," +
                "FoodID INTEGER," +
                "Size TEXT," +
                "Quantity INTEGER," +
                "PRIMARY KEY (OrderID, FoodID)," +
                "FOREIGN KEY (OrderID) REFERENCES `Order`(OrderID)," +
                "FOREIGN KEY (FoodID) REFERENCES Food(FoodID)" +
                ");";
        db.execSQL(Order_Detail);


        db.execSQL("INSERT INTO User (Name, Gender, Date_of_birth, Phone, Username, Password) VALUES " +
                "('Nguyen Van A', 'Male', '2001-01-01', '0123456789', 'vana', '123456')," +
                "('Tran Thi B', 'Female', '2000-02-02', '0987654321', 'thib', 'abcdef')," +
                "('Le Van C', 'Male', '1999-03-15', '0909090909', 'vanc', 'qwerty')," +
                "('Pham Thi D', 'Female', '2002-04-10', '0911223344', 'thid', 'password')," +
                "('Hoang Van E', 'Male', '2003-05-05', '0933445566', 'vane', 'hello123');");
        //5record Restaurant
        db.execSQL("INSERT INTO Restaurant (Name, Address, Phone, Image) VALUES " +
                "('Pizza Hut', '123 Le Loi, Q1', '0281234567', 'pizza')," +
                "('KFC', '45 Nguyen Hue, Q1', '0282345678', 'kfc')," +
                "('Lotteria', '78 Tran Hung Dao, Q5', '0283456789', 'lotteria')," +
                "('Highlands Coffee', '12 CMT8, Q3', '0284567890', 'coffee')," +
                "('The Sushi Bar', '88 Pham Ngu Lao, Q1', '0285678901', 'sushi');");

        // 10 record food
        db.execSQL("INSERT INTO Food (Name, Type, Description, Image, ResID) VALUES " +
                "('Pepperoni Pizza', 'Main Dish', 'Pizza with cheese and pepperoni', 'banhmi1', 1)," +
                "('Fried Chicken', 'Main Dish', 'Crispy fried chicken', 'banhmi2', 2)," +
                "('Burger', 'Main Dish', 'Beef burger with lettuce and tomato', 'banhmi3', 2)," +
                "('Cheese Pizza', 'Main Dish', 'Pizza with lots of cheese', 'banhmi4', 1)," +
                "('Shrimp Tempura', 'Side Dish', 'Deep-fried shrimp', 'banhmi5', 5)," +
                "('Green Tea', 'Drink', 'Cold green tea', 'banhmi6', 4)," +
                "('Cappuccino', 'Drink', 'Hot cappuccino coffee', 'banhmi7', 4)," +
                "('Salmon Sushi', 'Main Dish', 'Fresh salmon sushi', 'banhmi8', 5)," +
                "('French Fries', 'Side Dish', 'Crispy potato fries', 'banhmi9', 3)," +
                "('Bubble Tea', 'Drink', 'Milk tea with tapioca pearls', 'banhmi10', 4);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS OrderDetail");
        db.execSQL("DROP TABLE IF EXISTS `Order`");
        db.execSQL("DROP TABLE IF EXISTS Food");
        db.execSQL("DROP TABLE IF EXISTS Restaurant");
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }
}
