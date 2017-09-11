package com.example.android.newapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ShowData extends AppCompatActivity {
    private TextView name,gender,myProfile;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);


        Intent intent = getIntent();

        String sname = intent.getStringExtra("Name");
        String sgender = intent.getStringExtra("Gender");
        String semail = intent.getStringExtra("mobileno");
        String spass = intent.getStringExtra("pass");
        name= (TextView) findViewById(R.id.txtName);
        gender= (TextView) findViewById(R.id.txtGender);
      //  email= (TextView) findViewById(R.id.txtEmail);
      //  pass= (TextView) findViewById(R.id.txtPass);
        myProfile = (TextView) findViewById(R.id.myProfile);

        name.setText(sname);
        gender.setText(sgender);
       // email.setText(semail);
       // pass.setText(spass);


    }
    public void openProfile(View view) {

    }


    }

