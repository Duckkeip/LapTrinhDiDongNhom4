package com.example.sqlite.API;

import com.example.sqlite.model.Budget;
import com.example.sqlite.model.Category;
import com.example.sqlite.model.Income;
import com.example.sqlite.model.Expense;
import java.util.List;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import okhttp3.ResponseBody;

public interface ApiService {
// ======================= INCOME =========================

    @GET("income/get_income_by_user.php")
    Call<List<Income>> getIncomeByUser(@Query("username") String username);


    // Thêm income
    @FormUrlEncoded
    @POST("income/insert_income.php")
    Call<String> insertIncome(
            @Field("amount") double amount,
            @Field("note") String note,
            @Field("date") String date,
            @Field("category_id") int categoryId,
            @Field("user") String user
    );
    @FormUrlEncoded
    @POST("income/update_income.php")
    Call<ResponseBody> updateIncome(
            @Field("id") int id,
            @Field("amount") double amount,
            @Field("note") String note,
            @Field("date") String date,
            @Field("category_id") int categoryId,
            @Field("user") String user
    );

    @FormUrlEncoded
    @POST("income/delete_income.php")
    Call<ResponseBody> deleteIncome(@Field("id") int id);


    @GET("income/get_categories.php")
    Call<List<Category>> getIncomeCategories(@Query("type") String type);



// ======================= EXPENSE =========================

    @GET("expense/get_expense_by_user.php")
    Call<List<Expense>> getExpenseByUser(@Query("username") String username);

    @FormUrlEncoded
    @POST("expense/insert_expense.php")
    Call<String> insertExpense(
            @Field("amount") double amount,
            @Field("note") String note,
            @Field("date") String date,
            @Field("category_id") int categoryId,
            @Field("user") String user
    );

    @FormUrlEncoded
    @POST("expense/update_expense.php")
    Call<ResponseBody> updateExpense(
            @Field("id") int id,
            @Field("amount") double amount,
            @Field("note") String note,
            @Field("date") String date,
            @Field("category_id") int categoryId,
            @Field("user") String user
    );

    @FormUrlEncoded
    @POST("expense/delete_expense.php")
    Call<ResponseBody> deleteExpense(@Field("id") int id);

    @GET("expense/get_categories.php")
    Call<List<Category>> getExpenseCategories(@Query("type") String type);


// ======================= BUDGET =========================

    @FormUrlEncoded
    @POST("budget/insertBudget.php")
    Call<ResponseBody> insertBudget(
            @Field("category_id") int categoryId,
            @Field("amount") double amount,
            @Field("start_date") String startDate,
            @Field("end_date") String endDate,
            @Field("user") String user
    );

    @GET("budget/getBudgetsByUser.php")
    Call<List<Budget>> getBudgetsByUser(@Query("user") String user);

    @GET("budget/getAllCategories.php")
    Call<List<Category>> getAllCategories();


    @GET("budget/getTotalExpenseInBudget.php")
    Call<JsonObject> getTotalExpenseInBudget(
            @Query("user") String user,
            @Query("category_id") int categoryId,
            @Query("start_date") String startDate,
            @Query("end_date") String endDate
    );

    @FormUrlEncoded
    @POST("budget/updateBudget.php")
    Call<ResponseBody> updateBudget(
            @Field("id") int id,
            @Field("category_id") int categoryId,
            @Field("amount") double amount,
            @Field("start_date") String startDate,
            @Field("end_date") String endDate,
            @Field("user") String user
    );

    @GET("budget/deleteBudget.php")
    Call<ResponseBody> deleteBudget(@Query("id") int id);

}
