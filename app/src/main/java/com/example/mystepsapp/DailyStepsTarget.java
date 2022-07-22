package com.example.mystepsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.mystepsapp.Singleton.setStepsTarget;

public class DailyStepsTarget extends AppCompatActivity {
    EditText stepsTargetInput;
    Button stepsTargetBtn,backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_steps_target);
        
        stepsTargetBtn =findViewById(R.id.steps_goalbtn);
        stepsTargetInput =findViewById(R.id.steps_goal);
        backbtn = findViewById(R.id.back6);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),MyAchievments.class));
                startActivity(new Intent(getApplicationContext(),MyProfile.class));
                finish();
            }
        });
        
        stepsTargetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String target = stepsTargetInput.getText().toString().trim();

                if(TextUtils.isEmpty(target)){
                    //stepsTargetInput.setError("No value entered");
                    Toast.makeText(DailyStepsTarget.this, "Error: Please enter a steps target",Toast.LENGTH_SHORT).show();
                    return;
                }else try{

                    Integer.parseInt(target);
                    setStepsTarget(target);
                    Toast.makeText(DailyStepsTarget.this, "New steps target succesfully set", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    //stepsTargetInput.setError("Please enter a numeric value");
                    Toast.makeText(DailyStepsTarget.this, "Error: Please enter a numeric value",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }
}