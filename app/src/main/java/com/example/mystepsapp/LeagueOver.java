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

public class LeagueOver extends AppCompatActivity {
    TextView finalPostxt;
    Button playAgain, exit;
    DatabaseReference root;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_over);

        finalPostxt = findViewById(R.id.final_position);
        playAgain = findViewById(R.id.yes);
        exit = findViewById(R.id.no);

        userID = fAuth.getCurrentUser().getUid();
        root = database.getReference("users").child(userID);
        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(Integer.parseInt(snapshot.child("league_position").getValue().toString()) == 1) {
                    finalPostxt.setText("Congratulations you WON last months mysteps app league, would you like to enter this months competition?");
                }else if(Integer.parseInt(snapshot.child("league_position").getValue().toString()) == 2){
                    finalPostxt.setText("So so close - you finished 2nd in the mySteps app league for last month, would you like to enter this months competition?");
                }else if(Integer.parseInt(snapshot.child("league_position").getValue().toString()) > 2 && Integer.parseInt(snapshot.child("league_position").getValue().toString()) < 6){
                    finalPostxt.setText("Thats quite an achievement you finished in this months mysteps app league top 5, with your official position: " + snapshot.child("league_position").getValue().toString()+" would you like to enter this months competition?");

                }else{
                    finalPostxt.setText("congratulations you have succesfully completed this months mysteps app league, you rank amongst your friends was: " + snapshot.child("league_position").getValue().toString()+" would you like to enter this months competition?");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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