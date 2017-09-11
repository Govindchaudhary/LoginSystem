package com.example.android.newapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SignUp extends AppCompatActivity {
    Button b;
    RadioGroup rg;
    EditText etName,etPass,etMobileNo;
    RadioButton r1,r2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final StudentDbHelper dbHelper = new StudentDbHelper(this);
        b = (Button) findViewById(R.id.button);
        etPass =(EditText)findViewById(R.id.input_pass);
        etMobileNo =(EditText)findViewById(R.id.input_MobileNo);
        etName = (EditText) findViewById(R.id.input_name);
        r1 =(RadioButton)findViewById(R.id.f);
        r2 =(RadioButton)findViewById(R.id.m);
        rg = (RadioGroup)findViewById(R.id.radioGroup);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String salute="";
                if(r1.isChecked())
                {
                    salute = "Ms";
                }
                else
                {
                    salute = "Mr";
                }

                int i= rg.getCheckedRadioButtonId();
                RadioButton rb= (RadioButton)rg.findViewById(i);
                final String name = etName.getText().toString();
                final String mobileno = etMobileNo.getText().toString();
                final String pass = etPass.getText().toString();
                // databaseHandler.addContact(name,salute,mobileno,pass);
                SQLiteDatabase dbs = dbHelper.getWritableDatabase();



                ContentValues values = new ContentValues();
                values.put("sname", name); // Contact Name
                values.put("sgender",salute );
                values.put("smobile_no",mobileno);
                values.put("spass",pass);// Contact Phone Number

                // Inserting Row
                dbs.insert("user", null, values);
                dbs.close(); // Closing database connection


                SQLiteDatabase db = dbHelper.getReadableDatabase();

                Cursor c = db.query("user",new String[] { "sname",
                        "sgender", "smobile_no" ,"spass"},null,null,null,null,null);
                c.moveToLast();
                Intent intent = new Intent(SignUp.this,ShowData.class);
                intent.putExtra("Name",c.getString(0));
                intent.putExtra("Gender",c.getString(1));
                intent.putExtra("mobileno",c.getString(2));
                intent.putExtra("pass",c.getString(3));
                startActivity(intent);

            }
        });
    }
}

