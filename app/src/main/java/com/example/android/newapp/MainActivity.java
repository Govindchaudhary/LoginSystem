package com.example.android.newapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button b;
    TextView login;
    EditText etpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final StudentDbHelper dbHelper2 = new StudentDbHelper(this);
        b = (Button) findViewById(R.id.button);
        login = (TextView) findViewById(R.id.textView);
        etpassword = (EditText) findViewById(R.id.editText);


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUp.class);
                startActivity(intent);
            }
        });




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Password = etpassword.getText().toString();

                SQLiteDatabase sqldb = dbHelper2.getReadableDatabase();
                String Query = "SELECT * FROM user WHERE spass='" +Password +"'" ;

                final   Cursor cursor = sqldb.rawQuery(Query, null);


                final boolean exists = cursor.moveToFirst();

                if (exists==true) {

                    Intent intent2 = new Intent(MainActivity.this, UserProfile.class);
                    intent2.putExtra("Name", cursor.getString(0));
                    intent2.putExtra("Gender", cursor.getString(1));
                    intent2.putExtra("mobileno", cursor.getString(2));

                    cursor.close();


                    startActivity(intent2);
                }
                else
                    Toast.makeText(getApplicationContext(),"Invalid Password",Toast.LENGTH_SHORT
                    ).show();


            }
        });





    }
}