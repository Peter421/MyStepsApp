package com.example.mystepsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.mystepsapp.Singleton.fAuth;
import static com.example.mystepsapp.Singleton.handlednull;
import static com.example.mystepsapp.Singleton.inLeague;
import static com.example.mystepsapp.Singleton.play;
import static com.example.mystepsapp.Singleton.winnerAssignedF;

public class EnterLeague extends AppCompatActivity {
    TextView question;
    Button playAgain, exit;
    DatabaseReference root;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_league);

        question = findViewById(R.id.question);
        playAgain = findViewById(R.id.yes2);
        exit = findViewById(R.id.no2);

        userID = fAuth.getCurrentUser().getUid();

        question.setText("would you like to enter this months competition?");


        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
                inLeague();
                winnerAssignedF(userID);
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
                winnerAssignedF(userID);
                handlednull();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();

            }
        });

    }
}