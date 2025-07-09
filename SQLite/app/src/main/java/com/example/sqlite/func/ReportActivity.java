package com.example.sqlite.func;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.sqlite.API.ApiClient;
import com.example.sqlite.API.ApiService;
import com.example.sqlite.MainActivity_Final;
import com.example.sqlite.R;
import com.example.sqlite.model.Expense;
import com.example.sqlite.model.Income;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity extends AppCompatActivity {

    Spinner spinnerDataType, spinnerChartType;
    BarChart barChart;
    PieChart pieChart;
    LineChart lineChart;
    Button btnBack;

    List<Income> incomeList;// thu
    List<Expense> expenseList; // chi

    Button btnTimeFilter;
    int timeFilter = 0; // 0 = Ngày, 1 = Tuần, 2 = Tháng
    String[] timeOptions = {"Ngày", "Tuần", "Tháng"};
    private ApiService apiService;
    private String currentUser; // Lưu username

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
        currentUser = prefs.getString("username", "");

        apiService = ApiClient.getClient().create(ApiService.class);



        spinnerChartType = findViewById(R.id.spinnerChartType);
        barChart = findViewById(R.id.barChart);
        pieChart = findViewById(R.id.pieChart);
        lineChart = findViewById(R.id.lineChart);
        btnBack = findViewById(R.id.btnBack);
        btnTimeFilter = findViewById(R.id.btnTimeFilter);


        btnBack.setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity_Final.class)));

        spinnerDataType = findViewById(R.id.spinnerDataType);
        spinnerChartType = findViewById(R.id.spinnerChartType);

        incomeList = new ArrayList<>();
        expenseList = new ArrayList<>();

        loadIncomeData();
        loadExpenseData();

        spinnerChartType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerDataType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnTimeFilter.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(ReportActivity.this, btnTimeFilter);
            popup.getMenu().add("Ngày");
            popup.getMenu().add("Tuần");
            popup.getMenu().add("Tháng");

            popup.setOnMenuItemClickListener(item -> {
                String selected = item.getTitle().toString();
                btnTimeFilter.setText("Thống kê: " + selected);

                // Cập nhật giá trị timeFilter
                switch (selected) {
                    case "Ngày": timeFilter = 0; break;
                    case "Tuần": timeFilter = 1; break;
                    case "Tháng": timeFilter = 2; break;
                }

                updateChart();
                return true;
            });

            popup.show();
        });
    }
    private void updateChart() {
        int dataType = spinnerDataType.getSelectedItemPosition(); // 0 = income, 1 = expense
        int chartType = spinnerChartType.getSelectedItemPosition(); // 0 = bar, 1 = pie, 2 = line

        if (dataType == 0) {
            List<Income> filteredIncome = filterIncomeByTime(incomeList);
            switch (chartType) {
                case 0: showBarChartIncome(filteredIncome); break;
                case 1: showPieChartIncome(filteredIncome); break;
                case 2: showLineChartIncome(filteredIncome); break;
            }
        } else {
            List<Expense> filteredExpense = filterExpenseByTime(expenseList);
            switch (chartType) {
                case 0: showBarChartExpense(filteredExpense); break;
                case 1: showPieChartExpense(filteredExpense); break;
                case 2: showLineChartExpense(filteredExpense); break;
            }
        }
    }
    private void showBarChartIncome(List<Income> list) {
        barChart.setVisibility(View.VISIBLE);
        pieChart.setVisibility(View.GONE);
        lineChart.setVisibility(View.GONE);

        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            entries.add(new BarEntry(i, (float) list.get(i).getAmount()));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Khoản thu");
        barChart.setData(new BarData(dataSet));
        barChart.invalidate();
    }

    private void showPieChartIncome(List<Income> list) {
        pieChart.setVisibility(View.VISIBLE);
        barChart.setVisibility(View.GONE);
        lineChart.setVisibility(View.GONE);

        List<PieEntry> entries = new ArrayList<>();
        for (Income i : list) {
            entries.add(new PieEntry((float) i.getAmount(), i.getCategoryName()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Khoản thu");
        pieChart.setData(new PieData(dataSet));
        pieChart.invalidate();
    }

    private void showLineChartIncome(List<Income> list) {
        lineChart.setVisibility(View.VISIBLE);
        barChart.setVisibility(View.GONE);
        pieChart.setVisibility(View.GONE);

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            entries.add(new Entry(i, (float) list.get(i).getAmount()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Khoản thu");
        lineChart.setData(new LineData(dataSet));
        lineChart.invalidate();
    }

    // Expense
    private void showBarChartExpense(List<Expense> list) {
        barChart.setVisibility(View.VISIBLE);
        pieChart.setVisibility(View.GONE);
        lineChart.setVisibility(View.GONE);

        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            entries.add(new BarEntry(i, (float) list.get(i).getAmount()));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Khoản chi");
        barChart.setData(new BarData(dataSet));
        barChart.invalidate();
    }

    private void showPieChartExpense(List<Expense> list) {
        pieChart.setVisibility(View.VISIBLE);
        barChart.setVisibility(View.GONE);
        lineChart.setVisibility(View.GONE);

        List<PieEntry> entries = new ArrayList<>();
        for (Expense e : list) {
            entries.add(new PieEntry((float) e.getAmount(), e.getCategoryName()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Khoản chi");
        pieChart.setData(new PieData(dataSet));
        pieChart.invalidate();
    }

    private void showLineChartExpense(List<Expense> list) {
        lineChart.setVisibility(View.VISIBLE);
        barChart.setVisibility(View.GONE);
        pieChart.setVisibility(View.GONE);

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            entries.add(new Entry(i, (float) list.get(i).getAmount()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Khoản chi");
        lineChart.setData(new LineData(dataSet));
        lineChart.invalidate();
    }

    private List<Income> filterIncomeByTime(List<Income> original) {
        List<Income> filtered = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for (Income income : original) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(income.getDate());
                calendar.setTime(date);

                switch (timeFilter) {
                    case 0: // Ngày
                        if (isToday(calendar)) filtered.add(income);
                        break;
                    case 1: // Tuần
                        if (isThisWeek(calendar)) filtered.add(income);
                        break;
                    case 2: // Tháng
                        if (isThisMonth(calendar)) filtered.add(income);
                        break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return filtered;
    }

    private List<Expense> filterExpenseByTime(List<Expense> original) {
        List<Expense> filtered = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for (Expense expense : original) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(expense.getDate());
                calendar.setTime(date);

                switch (timeFilter) {
                    case 0: // Ngày
                        if (isToday(calendar)) filtered.add(expense);
                        break;
                    case 1: // Tuần
                        if (isThisWeek(calendar)) filtered.add(expense);
                        break;
                    case 2: // Tháng
                        if (isThisMonth(calendar)) filtered.add(expense);
                        break;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return filtered;
    }

    private boolean isToday(Calendar date) {
        Calendar now = Calendar.getInstance();
        return date.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
                date.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR);
    }

    private boolean isThisWeek(Calendar date) {
        Calendar now = Calendar.getInstance();
        return date.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
                date.get(Calendar.WEEK_OF_YEAR) == now.get(Calendar.WEEK_OF_YEAR);
    }

    private boolean isThisMonth(Calendar date) {
        Calendar now = Calendar.getInstance();
        return date.get(Calendar.YEAR) == now.get(Calendar.YEAR) &&
                date.get(Calendar.MONTH) == now.get(Calendar.MONTH);
    }

    private void loadIncomeData() {
        apiService.getIncomeByUser(currentUser).enqueue(new Callback<List<Income>>() {
            @Override
            public void onResponse(Call<List<Income>> call, Response<List<Income>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    incomeList.clear();
                    incomeList.addAll(response.body());
                    updateChart(); // Vẽ lại biểu đồ sau khi có dữ liệu
                }
            }

            @Override
            public void onFailure(Call<List<Income>> call, Throwable t) {
                Toast.makeText(ReportActivity.this, "Lỗi tải dữ liệu thu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadExpenseData() {
        apiService.getExpenseByUser(currentUser).enqueue(new Callback<List<Expense>>() {
            @Override
            public void onResponse(Call<List<Expense>> call, Response<List<Expense>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    expenseList.clear();
                    expenseList.addAll(response.body());
                    updateChart(); // Vẽ lại biểu đồ sau khi có dữ liệu
                }
            }

            @Override
            public void onFailure(Call<List<Expense>> call, Throwable t) {
                Toast.makeText(ReportActivity.this, "Lỗi tải dữ liệu chi", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

