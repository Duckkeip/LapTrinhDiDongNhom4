package com.example.helloworldfinal;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.core.text.HtmlCompat;

//cac id cua layouts
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView txtDate,reddot;
    Button btnTime,btnTime1;
    EditText editReason;
    RadioGroup radioGroupType;
    RadioButton radioNormal, radioSpecial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 👉 nhớ đúng với tên file XML bạn dùng
        reddot = findViewById(R.id.reddot);

        // Ánh xạ các thành phần trong giao diện
        txtDate = findViewById(R.id.txtDate);
        btnTime = findViewById(R.id.btnTime);
        editReason = findViewById(R.id.editReason);
        radioGroupType = findViewById(R.id.radioGroupType);
        radioNormal = findViewById(R.id.radioNormal);
        radioSpecial = findViewById(R.id.radioSpecial);

        // Gán sự kiện chọn ngày
        txtDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    MainActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        txtDate.setText(date);
                    },
                    year, month, day
            );
            datePickerDialog.show();
        });

        // Gán sự kiện chọn giờ
        btnTime.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    MainActivity.this,
                    (view, selectedHour, selectedMinute) -> {
                        String time = String.format("%02d:%02d", selectedHour, selectedMinute);
                        btnTime.setText(time);
                    },
                    hour, minute, true
            );
            timePickerDialog.show();
        });
        reddot.setText(HtmlCompat.fromHtml(getString(R.string.reddot), HtmlCompat.FROM_HTML_MODE_LEGACY));

    }

}
