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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static com.example.mystepsapp.Singleton.database;
import static com.example.mystepsapp.Singleton.displayfriendf;
import static com.example.mystepsapp.Singleton.fAuth;
import static com.example.mystepsapp.Singleton.myRef;
import static com.example.mystepsapp.Singleton.yourRef;
import static com.example.mystepsapp.Singleton.yourdatabase;

public class LeagueTable extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    String userID = fAuth.getCurrentUser().getUid();
    private DatabaseReference root;
    private TableAdapter adapter;
    private ArrayList<PositionModel> dlist;
    private ArrayList<PositionModel> list;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_table);

        back = findViewById(R.id.backbtn1);

        recyclerView = findViewById(R.id.recyclerview2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        dlist = new ArrayList<>();
        list = new ArrayList<>();
        adapter = new TableAdapter(this,list);
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<String> fids = new ArrayList<>();

        recyclerView.setAdapter(adapter);
       yourRef = yourdatabase.getReference().child("users");
        root = db.getReference().child("users");
        root.addListenerForSingleValueEvent(new ValueEventListener() {

            int i =0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child(userID).child("in_league").getValue().toString().equals("false")){
                    startActivity(new Intent(getApplicationContext(),EnterLeague.class));
                    finish();
                }

                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ids.add(dataSnapshot.getKey());
                   // System.out.println(snapshot.child(ids.get(i)).child("Daily_steps").getValue().toString());
                    for(DataSnapshot dataSnapshot2 : snapshot.child(userID).child("friends").getChildren()) {
                        //System.out.println(dataSnapshot2.getValue().toString());
                    if(dataSnapshot2.getValue().toString().equals(snapshot.child(ids.get(i)).child("username").getValue().toString()) && snapshot.child(ids.get(i)).child("in_league").getValue().toString().equals("true")) {
                        fids.add(ids.get(i));

                        //System.out.println(snapshot.child(ids.get(i)).child("username").getValue().toString());

                        PositionModel model = new PositionModel("0", snapshot.child(ids.get(i)).child("username").getValue().toString(), snapshot.child(userID).child("Date").child("time").child("date").getValue().toString(),
                                snapshot.child(ids.get(i)).child("Daily_steps").getValue().toString(), snapshot.child(ids.get(i)).child("Total_steps").getValue().toString());
                        dlist.add(model);

                    }

                    }
                    i++;

                }
                dlist.add(new PositionModel("0",snapshot.child(userID).child("username").getValue().toString(),snapshot.child(userID).child("Date").child("time").child("date").getValue().toString(),
                        snapshot.child(userID).child("Daily_steps").getValue().toString(),snapshot.child(userID).child("Total_steps").getValue().toString()));

                Collections.sort(dlist,new StepComparator());
                int j=1;
                for(int i=0;i<dlist.size();i++){
                    dlist.get(i).setPosition(String.valueOf(j));
                   // childUpdates.put(fids.get(k)+"/league_position", dlist.get(k).getPosition());
                    PositionModel model2 = new PositionModel(dlist.get(i).getPosition(),dlist.get(i).getUsername(),dlist.get(i).getDays(),
                            dlist.get(i).getDailysteps(),dlist.get(i).getTotalsteps());
                    //String id = snapshot.child(ids.get(i)).toString();
                    //childUpdates.put(yourRef.child(ids.get(i)).child("league_position").toString(), String.valueOf(j));

                  //  root.child(ids.get(i)).child("league_position").updateChildren(ids.get(i),dlist.get(i).getPosition());

                   list.add(model2);
                   j++;
               }
               //get id associated with username then print pos if(snapshot.child(fids.get(0)).child(list.get(1).getUsername()).exists())
                fids.add(userID);
                adapter.notifyDataSetChanged();
                Map<String, Object> childUpdates = new HashMap<>();
                for(int k=0;k<list.size();k++){
                    for(int l=0;l<fids.size();l++){
                        //System.out.println(snapshot.child(fids.get(l)).child(list.get(k).getUsername()).exists());
                    if(snapshot.child(fids.get(l)).child("username").getValue().toString() == list.get(k).getUsername()) {
                        childUpdates.put(fids.get(l) + "/league_position", list.get(k).getPosition());

                   }
                  }

                }
               /* myRef = database.getReference("users").child(userID).child("league_position");
                for(int k=0;k<list.size();k++) {
                    if(list.get(k).getUsername().equals(snapshot.child(userID).child("username").getValue().toString()))
                        myRef.setValue(list.get(k).getPosition());
                }*/
                try {


                yourRef.updateChildren(childUpdates);
                }catch (Exception e){}
System.out.println("size"+ids.size());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

    }
}