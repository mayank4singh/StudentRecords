package com.example.studentrecords;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
        DatabaseHelper databaseHelper;
        EditText text_id, text_name, text_email, text_courseCount;
        Button Add, view, update, delete, viewAll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        text_id = findViewById(R.id.id);
        text_name = findViewById(R.id.Name);
        text_email = findViewById(R.id.email);
        text_courseCount = findViewById(R.id.courseCount);

        Add = findViewById(R.id.Add);
        view = findViewById(R.id.View);
        update = findViewById(R.id.Update);
        delete = findViewById(R.id.Delete);
        viewAll = findViewById(R.id.View_all);

        AddData();
        getData();
        viewAll();
        updateData();
        deleteData();
    }

    public void AddData(){
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ADDDED = databaseHelper.insertData(
                        text_name.getText().toString(),
                        text_email.getText().toString(),
                        text_courseCount.getText().toString());
                if(ADDDED == true){
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void getData(){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = text_id.getText().toString();

                if (id.equals(String.valueOf(""))){
                    text_id.setError("Enter ID");
                    return;
                }

                Cursor cursor = databaseHelper.GetData(id);
                String data = null;

                if (cursor.moveToNext()){
                    data = "ID: "+ cursor.getString(0) +"\n"+
                            "Name: "+ cursor.getString(1) +"\n"+
                            "Email: "+ cursor.getString(2) +"\n"+
                            "Course Count: "+ cursor.getString(3) +"\n";
                }
                showMessage("Data: ", data);


            }
        });
    }
    public void viewAll(){
        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = databaseHelper.getAllData();

                //small test
                if (cursor.getCount() == 0){
                    showMessage("Error", "Nothing found in DB");
                    return;
                }

                StringBuffer buffer = new StringBuffer();

                while (cursor.moveToNext()){
                    buffer.append("ID: "+cursor.getString(0)+"\n");
                    buffer.append("Name: "+cursor.getString(1)+"\n");
                    buffer.append("Email: "+cursor.getString(2)+"\n");
                    buffer.append("CC: "+cursor.getString(3)+"\n\n");
                }
                showMessage("All data", buffer.toString());

            }
        });


    }
    public void updateData(){
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate = databaseHelper.UpdateData(text_id.getText().toString(),
                        text_name.getText().toString(),
                        text_email.getText().toString(),
                        text_courseCount.getText().toString());

                if (isUpdate == true){
                    Toast.makeText(MainActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "OOPSS!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    public void deleteData(){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRow = databaseHelper.DeleteData(text_id.getText().toString());

                if (deletedRow > 0){
                    Toast.makeText(MainActivity.this, "Delete Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "OOPSSS!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }


    private void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}



