package com.example.mystepsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.mystepsapp.Singleton.database;
import static com.example.mystepsapp.Singleton.database2;
import static com.example.mystepsapp.Singleton.fAuth;
import static com.example.mystepsapp.Singleton.logOut;
import static com.example.mystepsapp.Singleton.myRef;
import static com.example.mystepsapp.Singleton.unaddFriend;
import static com.example.mystepsapp.Singleton.userID;
import static com.example.mystepsapp.Singleton.yourRef;

public class DeleteAccount extends AppCompatActivity {
Button yes,no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        yes = findViewById(R.id.btndelete);
        no = findViewById(R.id.btnback);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userID = fAuth.getCurrentUser().getUid();
                ArrayList<String> friends = new ArrayList<>();
                myRef = database.getReference("users").child(userID).child("friends");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            friends.add(dataSnapshot.getValue().toString());
                            unaddFriend(dataSnapshot.getValue().toString());
                            System.out.println(dataSnapshot.getValue().toString());

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        yourRef = database2.getReference("users").child(userID);
                        yourRef.removeValue();
                        fAuth.getCurrentUser().delete();
                        logOut();
                        startActivity(new Intent(getApplicationContext(),Register.class));
                        finish();
                    }
                }, 5000);


            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),MyAchievments.class));
                startActivity(new Intent(getApplicationContext(),MyProfile.class));

                finish();

            }
        });
    }
}