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

import static com.example.mystepsapp.Singleton.fAuth;

public class MyAchievments extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    String userID = fAuth.getCurrentUser().getUid();
    private DatabaseReference ref;
    private StatsAdapter adapter;
    private ArrayList<StatsModel> list;
    private Button backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_achievments);

        backbtn = findViewById(R.id.back5);

        recyclerView = findViewById(R.id.recylcerview5);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new StatsAdapter(this,list);
        recyclerView.setAdapter(adapter);

        ref = db.getReference().child("users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(userID).child("league_position").exists()){

                    StatsModel model= new StatsModel("current league position: "+snapshot.child(userID).child("league_position").getValue().toString());
                    list.add(model);
                }
                if(snapshot.child(userID).child("Daily_steps").exists()){
                    StatsModel model= new StatsModel("Todays steps: "+snapshot.child(userID).child("Daily_steps").getValue().toString());
                    list.add(model);
                }

                if(snapshot.child(userID).child("titles").exists()){
                    StatsModel model= new StatsModel("Title wins: "+snapshot.child(userID).child("titles").getValue().toString());
                    list.add(model);
                }
                if(snapshot.child(userID).child("step_highscore").exists()){
                    StatsModel model= new StatsModel("highest step count: "+snapshot.child(userID).child("step_highscore").getValue().toString());
                    list.add(model);
                }
                if(snapshot.child(userID).child("Total_steps").exists()){
                    StatsModel model= new StatsModel("total steps in competition: "+snapshot.child(userID).child("Total_steps").getValue().toString());
                    list.add(model);
                }
                ArrayList<String> fids = new ArrayList<>();
                ArrayList<String> ids = new ArrayList<>();
                int i=0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ids.add(dataSnapshot.getKey());

                }
                for (DataSnapshot dataSnapshot2 : snapshot.child(userID).child("friends").getChildren()) {
                    //System.out.println(dataSnapshot2.getValue().toString());
                    if (dataSnapshot2.getValue().toString().equals(snapshot.child(userID).child("username").getValue().toString()) && snapshot.child(ids.get(i)).child("in_league").getValue().toString().equals("true")) {
                        fids.add(ids.get(i));
                    }
                    i++;
                }
                int count=0;
                for(int j=0;j<fids.size();j++){
                    if(Integer.parseInt(snapshot.child(userID).child("step_highscore").getValue().toString()) > Integer.parseInt(snapshot.child(fids.get(i)).child("step_highscore").getValue().toString())){
                        count++;
                    }
                }
               if(count == fids.size() ){
                   StatsModel model= new StatsModel("Leading by Example - Highest single day step count out of all friends");
                   list.add(model);
               }
                if(snapshot.child(userID).child("Daily_Goal").exists()){
                    if(Integer.parseInt(snapshot.child(userID).child("target_days").getValue().toString())!=0){
                        Double consitency = Double.parseDouble(snapshot.child(userID).child("targets_met").getValue().toString())/Integer.parseInt(snapshot.child(userID).child("target_days").getValue().toString())*100;
                        if(consitency >79.9){
                            StatsModel model= new StatsModel("Consistency Rank: elite - "+String.valueOf(consitency)+"%");
                            list.add(model);
                        } else if(consitency >69.9 && consitency < 79.9){
                            StatsModel model= new StatsModel("Consistency Rank: warior - "+String.valueOf(consitency)+"%");
                            list.add(model);
                        }else if(consitency >59.9 && consitency < 69.9){
                            StatsModel model= new StatsModel("Consistency Rank: Good - "+String.valueOf(consitency)+"%");
                            list.add(model);
                        }
                        else if(consitency >49.9 && consitency < 59.9){
                            StatsModel model= new StatsModel("Consistency Rank: moderate - "+String.valueOf(consitency)+"%");
                            list.add(model);
                        }
                        else if(consitency < 49.9){
                            StatsModel model= new StatsModel("Consistency Rank: poor - "+String.valueOf(consitency)+"%");
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

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),MyAchievments.class));
                startActivity(new Intent(getApplicationContext(),MyProfile.class));
                finish();
            }
        });
    }
}