package com.example.mystepsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.mystepsapp.Singleton.fAuth;

public class FriendList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private FirebaseDatabase db2 = FirebaseDatabase.getInstance();
    String userID = fAuth.getCurrentUser().getUid();
    private DatabaseReference root;
    private DatabaseReference root2;
    private FriendAdapter adapter;
    private ArrayList<FriendModel> list;
    private Button backbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);


        backbtn = findViewById(R.id.back1);

        recyclerView = findViewById(R.id.recyclerview3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        list = new ArrayList<>();
        adapter = new FriendAdapter(this, list);

        recyclerView.setAdapter(adapter);
        root = db.getReference().child("users");
        root.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<String> ids = new ArrayList<>();
            int i = 0;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ids.add(dataSnapshot.getKey());

                    for (DataSnapshot dataSnapshot2 : snapshot.child(userID).child("friends").getChildren()) {
                        if (dataSnapshot2.getValue().toString().equals(snapshot.child(ids.get(i)).child("username").getValue().toString())) {

                            FriendModel model = new FriendModel( snapshot.child(ids.get(i)).child("username").getValue().toString());
                            list.add(model);
                        }

                    }
                    i++;
                }
                adapter.notifyDataSetChanged();
            }
// snapshot.id.username
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root2 =db2.getReference().child("users").child(userID);
        root2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("disp_friend").getValue().toString().equals("true")){
                    startActivity(new Intent(getApplicationContext(),FriendStats.class));
                     finish();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),MyAchievments.class));
                startActivity(new Intent(getApplicationContext(),FriendInbetween.class));
                finish();
            }
        });
    }
}