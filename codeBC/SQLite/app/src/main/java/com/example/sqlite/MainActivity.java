package com.example.sqlite;

import android.content.Intent;
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
import com.example.sqlite.sqlite.DBHelper;
import com.example.sqlite.sqlite.EmployeeDao;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EmployeeAdapter employeeAdapter;
    private ListView lvEmployees;

    private String employeeId;
    private List<Employee> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        database.close();

        findViewById(R.id.btnEdit).setOnClickListener(this);
        findViewById(R.id.btnInsert).setOnClickListener(this);
        findViewById(R.id.btnDelete).setOnClickListener(this);

        lvEmployees = findViewById(R.id.lvEmployees);


        EmployeeDao dao = new EmployeeDao(this);

        list= dao.getAll();


        employeeAdapter = new EmployeeAdapter(this, list);
        lvEmployees.setAdapter(employeeAdapter);
        lvEmployees.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Employee emp = list.get(i);
                employeeId = emp.getId();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        EmployeeDao dao =new EmployeeDao(this);
        List<Employee> updatedList = dao.getAll();

        list.clear();
        updatedList.forEach(item->list.add(item));

        employeeAdapter.notifyDataSetChanged();
    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, AddOrEditEmployeeActivity.class);
        int id = view.getId();
            if(id == R.id.btnInsert){
                startActivity(intent);
        }
            else if(id == R.id.btnEdit){
                    if(employeeId == null){
                        Toast.makeText(this,"Employee Id must be Selected",Toast.LENGTH_SHORT).show();
                        return;
                    }

                Bundle bundle = new Bundle();
                bundle.putString("id", employeeId);
                intent.putExtra("data",bundle);

                startActivity(intent);

            }
            else if (id == R.id.btnDelete){
                if(employeeId == null){
                    Toast.makeText(this,"Employee Id must be selected", Toast.LENGTH_SHORT).show();
                    return;
                }


                EmployeeDao dao = new EmployeeDao(this);
                dao.delete(employeeId);
                employeeId = null;
                Toast.makeText(this ,"Employee was deleted", Toast.LENGTH_SHORT).show();

                onResume();
            }

    }


}