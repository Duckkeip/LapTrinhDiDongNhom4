package com.example.sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sqlite.model.Employee;
import com.example.sqlite.sqlite.EmployeeDao;

public class AddOrEditEmployeeActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etEmployeeId, etName, etSalary;
    private Button btnSave,btnListEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_add_or_edit_employee);

            etEmployeeId = findViewById(R.id.etEmployeeId);
            etName = findViewById(R.id.etName);
            etSalary = findViewById(R.id.etSalary);

            btnSave = findViewById(R.id.btnSave);
            btnListEmployee = findViewById(R.id.btnListEmployee);

            btnSave.setOnClickListener(this);
            btnListEmployee.setOnClickListener(this);


            readEmployee();

    }
        private void readEmployee(){
            Intent intent = getIntent();
            Bundle bundle = intent.getBundleExtra("data");
            if(bundle == null){
                return;
            }
        String id =  bundle.getString("id");

        EmployeeDao dao = new EmployeeDao(this);
        Employee emp = dao.getById(id);

        etEmployeeId.setText(emp.getId());
        etName.setText(emp.getName());
        etSalary.setText(""+ emp.getSalary());

        btnSave.setText("Update");

        }
    @Override
    public void onClick(View view) {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        int id = view.getId();
        if(id == R.id.btnSave) {

            // Tạo đối tượng DAO
            EmployeeDao dao = new EmployeeDao(this);

            // Tạo đối tượng Employee và gán dữ liệu từ EditText
            Employee emp = new Employee();
            emp.setId(etEmployeeId.getText().toString());
            emp.setName(etName.getText().toString());
            emp.setSalary(Float.parseFloat(etSalary.getText().toString()));

            if (btnSave.getText().equals("Save")) {
                dao.insert(emp);
            } else {
                dao.update(emp);
            }
            // Gọi phương thức insert để lưu vào cơ sở dữ liệu
            dao.insert(emp);


            // Thông báo thành công
            Toast.makeText(this, "New employee has been saved!", Toast.LENGTH_SHORT).show();

        }
        else if(id == R.id.btnListEmployee) {
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);

                }
        }
    }
