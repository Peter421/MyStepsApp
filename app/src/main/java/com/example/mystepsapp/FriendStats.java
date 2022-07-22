package com.example.mystepsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;

import static com.example.mystepsapp.Singleton.displayfriendf;
import static com.example.mystepsapp.Singleton.fAuth;

public class FriendStats extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private FirebaseDatabase db2 = FirebaseDatabase.getInstance();
    String userID = fAuth.getCurrentUser().getUid();
    private DatabaseReference root3;
    private DatabaseReference ref;
    private StatsAdapter adapter;
    private ArrayList<StatsModel> list;
    String fid ="";
    private TextView currentFriend;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_stats);

        currentFriend = findViewById(R.id.curr_friend);
        back = findViewById(R.id.backbtn);


        recyclerView = findViewById(R.id.recyclerview4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new StatsAdapter(this,list);
        recyclerView.setAdapter(adapter);

        ref = db2.getReference().child("users").child(userID);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fid = snapshot.child("current_Friend").getValue().toString();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root3 = db.getReference().child("users");
        root3.addListenerForSingleValueEvent(new ValueEventListener() {
            //int k=0;
            int count=0;
            ArrayList<String> fids = new ArrayList<>();
            ArrayList<String> ids = new ArrayList<>();
            int i=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentFriend.setText(snapshot.child(fid).child("username").getValue().toString() +"'s Statistics");

                if(snapshot.child(fid).child("league_position").exists()){

                    StatsModel model= new StatsModel("current league position: "+snapshot.child(fid).child("league_position").getValue().toString());
                    list.add(model);
                }
                if(snapshot.child(fid).child("Daily_steps").exists()){
                    StatsModel model= new StatsModel("Todays steps: "+snapshot.child(fid).child("Daily_steps").getValue().toString());
                    list.add(model);
                }

                if(snapshot.child(fid).child("titles").exists()){
                    StatsModel model= new StatsModel("Title wins: "+snapshot.child(fid).child("titles").getValue().toString());
                    list.add(model);
                }
                if(snapshot.child(fid).child("step_highscore").exists()){
                    StatsModel model= new StatsModel("highest step count: "+snapshot.child(fid).child("step_highscore").getValue().toString());
                    list.add(model);
                }
                if(snapshot.child(fid).child("Total_steps").exists()){
                    StatsModel model= new StatsModel("total steps in competition: "+snapshot.child(fid).child("Total_steps").getValue().toString());
                    list.add(model);
                }

                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ids.add(dataSnapshot.getKey());
                    //System.out.println(ids.get(i));
                    i++;

                }


                for (DataSnapshot dataSnapshot2 : snapshot.child(fid).child("friends").getChildren()) {
                    System.out.println(dataSnapshot2.getValue().toString());
                    //System.out.println( snapshot.child(ids.get(k)).child("username").getValue().toString());
                    //System.out.println(dataSnapshot2.getValue().toString());
                    for (int k = 0; k < ids.size(); k++){
                        if (dataSnapshot2.getValue().toString().equals(snapshot.child(ids.get(k)).child("username").getValue().toString())/* && snapshot.child(ids.get(k)).child("in_league").getValue().toString().equals("true")*/) {
                            fids.add(ids.get(k));
                            //System.out.println(k);
                        }
                }
                }

                for(int j=0;j<fids.size();j++){
                    if(Integer.parseInt(snapshot.child(fid).child("step_highscore").getValue().toString()) > Integer.parseInt(snapshot.child(fids.get(j)).child("step_highscore").getValue().toString())){
                        count++;
                    }
                }

                if(count == fids.size()){
                    System.out.println(count+"=count, "+fids.size()+" friends" );
                    StatsModel model= new StatsModel("Leading by Example - Highest single day step count out of all friends");
                    list.add(model);
                }
                if(snapshot.child(fid).child("Daily_Goal").exists()){
                    if(Integer.parseInt(snapshot.child(fid).child("target_days").getValue().toString())!=0){
                        Double consitency = Double.parseDouble(snapshot.child(fid).child("targets_met").getValue().toString())/Integer.parseInt(snapshot.child(fid).child("target_days").getValue().toString());
                        if(consitency >79.9){
                            StatsModel model= new StatsModel("Consistency Rank: elite - "+String.valueOf(consitency));
                            list.add(model);
                        } else if(consitency >69.9 && consitency < 79.9){
                            StatsModel model= new StatsModel("Consistency Rank: warior - "+String.valueOf(consitency));
                            list.add(model);
                        }else if(consitency >59.9 && consitency < 69.9){
                            StatsModel model= new StatsModel("Consistency Rank: Good - "+String.valueOf(consitency));
                            list.add(model);
                        }
                        else if(consitency >49.9 && consitency < 59.9){
                            StatsModel model= new StatsModel("Consistency Rank: moderate - "+String.valueOf(consitency));
                            list.add(model);
                        }
                        else if(consitency < 49.9){
                            StatsModel model= new StatsModel("Consistency Rank: poor - "+String.valueOf(consitency));
                            list.add(model);
                        }

                    }

                }


                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayfriendf();
                startActivity(new Intent(getApplicationContext(),FriendList.class));
                finish();
            }
        });


    }
}