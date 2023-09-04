package com.example.sqlitecrud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Currency;

public class MainActivity extends AppCompatActivity {
    Button btnInsert, btnUpdate, btnDelete, btnView;
    EditText txtName, txtContact, txtDOB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = findViewById(R.id.txtName);
        txtContact = findViewById(R.id.txtContact);
        txtDOB = findViewById(R.id.txtDOB);

        btnInsert = findViewById(R.id.btnInsert);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnView = findViewById(R.id.btnView);

        DBHelper DB = new DBHelper(this);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                String contact = txtContact.getText().toString();
                String dob = txtDOB.getText().toString();

                Boolean checkDBop = DB.insertUserData(name, contact, dob);
                if(checkDBop == true){
                    Toast.makeText(MainActivity.this, "New record inserted successfully!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Unable to insert record!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //update OnClick listener
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();
                String contact = txtContact.getText().toString();
                String dob = txtDOB.getText().toString();

                Boolean checkUpdate = DB.updateUserData(name, contact, dob);
                if(checkUpdate == true){
                    Toast.makeText(MainActivity.this, "Record updated successfully!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Unable to update record!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //delete OnClick listener
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtName.getText().toString();

                Boolean checkDelete = DB.deleteUserData(name);
                if(checkDelete == true){
                    Toast.makeText(MainActivity.this, "Record deleted successfully!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Unable to delete record!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //view
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor records = DB.viewUserData();
                if(records.getCount() == 0){
                    Toast.makeText(MainActivity.this, "No Data Found!", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer allRecords = new StringBuffer();
                while(records.moveToNext()){
                    allRecords.append("Name: "+records.getString(0)+"\n");
                    allRecords.append("Contact: "+records.getString(1)+"\n");
                    allRecords.append("Date of Birth: "+records.getString(2)+"\n");
                }
                //Alert box showing all records
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("View All Records");
                builder.setMessage(allRecords.toString());
                builder.show();
            }
        });
    }
}