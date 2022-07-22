package com.example.mystepsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static com.example.mystepsapp.Singleton.logOut;

public class MyProfile extends AppCompatActivity {
    Button stepsTarget,logOut,delete,achievements,backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        stepsTarget = findViewById(R.id.stepstargetbtn);
        logOut = findViewById(R.id.logout);
        delete = findViewById(R.id.deletebtn);
        achievements = findViewById(R.id.my_stats);
        backbtn = findViewById(R.id.back4);

        stepsTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DailyStepsTarget.class));
                finish();

            }
        });

        achievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MyAchievments.class));
                finish();

            }
        });
       delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DeleteAccount.class));
                finish();

            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();

            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),MyAchievments.class));
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }
}