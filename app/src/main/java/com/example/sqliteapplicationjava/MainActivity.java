package com.example.sqliteapplicationjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //references to buttons and other controls on the layout
    Button btn_add, btn_view;
    EditText et_name, et_age;
    SwitchCompat switch_active;
    ListView customer_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.btn_add);
        btn_view = findViewById(R.id.btn_view);
        et_age = findViewById(R.id.et_age);
        et_name = findViewById(R.id.et_name);
        switch_active = findViewById(R.id.switch_active);

        //button listeners for add and view button
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerModel customerObject;
                try {
                    customerObject = new CustomerModel(-1, et_name.getText().toString(), Integer.parseInt(et_age.getText().toString()), switch_active.isChecked());
                    //Toast.makeText(MainActivity.this, "Add button clicked\n" + customerObject.toString(), Toast.LENGTH_LONG).show();
                } catch (Exception e){
                    Toast.makeText(MainActivity.this, "Input fields can't be empty!", Toast.LENGTH_LONG).show();
                    customerObject = new CustomerModel(-1, "error", 0, false);
                }

                DataBaseHandler dataBaseHelper = new DataBaseHandler(MainActivity.this);
                boolean success = dataBaseHelper.addOne(customerObject);
                Toast.makeText(MainActivity.this, "Success: " + success, Toast.LENGTH_LONG).show();
            }
        });

        btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "View button clicked", Toast.LENGTH_LONG).show();
            }
        });
    }
}