package com.example.mystepsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.mystepsapp.Singleton.checkUser;
import static com.example.mystepsapp.Singleton.displayfriendf;
import static com.example.mystepsapp.Singleton.displayfriendf;
import static com.example.mystepsapp.Singleton.fAuth;
import static com.example.mystepsapp.Singleton.getAuthInstance;
import static com.example.mystepsapp.Singleton.getUsername;
import static com.example.mystepsapp.Singleton.handled;
import static com.example.mystepsapp.Singleton.inLeague;
import static com.example.mystepsapp.Singleton.ini_dailyGoal;
import static com.example.mystepsapp.Singleton.ini_targetDays;
import static com.example.mystepsapp.Singleton.ini_targetsMet;
import static com.example.mystepsapp.Singleton.myRef;
import static com.example.mystepsapp.Singleton.notifyAddFriend_f;
import static com.example.mystepsapp.Singleton.play;
import static com.example.mystepsapp.Singleton.setHigheststep;
import static com.example.mystepsapp.Singleton.updateSteps;
import static com.example.mystepsapp.Singleton.updateTotalSteps;
import static com.example.mystepsapp.Singleton.winnerAssignedF_init;
import static com.example.mystepsapp.Singleton.writeDate;
import static com.example.mystepsapp.Singleton.writeUserName;

public class Register extends AppCompatActivity {
EditText Email,Password;
Button registerbtn;
TextView loginpg;

//FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initialise
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.password);
        registerbtn = findViewById(R.id.Registerbtn);
        loginpg = findViewById(R.id.Loginpagebtn2);

        getAuthInstance();// get db instance

        //if user is logged in start main activity
        if(checkUser()==true){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


        //register button on click events
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString().trim();
                String password = Password.getText().toString().trim();

                if(TextUtils.isEmpty(password)){
                    Password.setError("Password is required");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    Email.setError("email is required");
                    return;
                }

                if(password.length() <5){
                    Password.setError("Password must be atleast 5 characters long");
                    return;
                }

              //upon succes create user and start main activity or if error display message
              fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      if(task.isSuccessful()){
                          Toast.makeText(Register.this, "user created", Toast.LENGTH_SHORT).show();

                          writeUserName(getUsername(email));
                          writeDate();
                          play();
                          inLeague();
                          winnerAssignedF_init();
                          updateSteps(0);
                          setHigheststep(0);
                          updateTotalSteps(0);
                          displayfriendf();
                          handled();
                          notifyAddFriend_f();
                         // new_friend();
                          ini_targetDays();
                          ini_targetsMet();
                          ini_dailyGoal();
                          startActivity(new Intent(getApplicationContext(),MainActivity.class));

                      }else {
                          Toast.makeText(Register.this, "Error" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                      }
                  }
              });
            }
        });

        //login page link onclick event
        loginpg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}