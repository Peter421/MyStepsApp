
package com.example.mystepsapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

//import static android.content.Context.SENSOR_SERVICE;
//import static androidx.core.content.ContextCompat.getSystemService;

public class Singleton {
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static FirebaseDatabase database2 = FirebaseDatabase.getInstance();
    static FirebaseDatabase fdatabase = FirebaseDatabase.getInstance();
    static FirebaseDatabase yourdatabase = FirebaseDatabase.getInstance();
    static DatabaseReference myRef;
    static DatabaseReference myRef2;
    static DatabaseReference yourRef;
    static DatabaseReference fRef;
    // = database.getReference("user").child(userID);
    static String userID;
    static String username;
    //static FirebaseFirestore fstore= FirebaseFirestore.getInstance();
    static FirebaseAuth fAuth;

    //update steps
    static void updateSteps(int steps){
        try {
            userID = fAuth.getCurrentUser().getUid();
            myRef = database.getReference("users").child(userID).child("Daily_steps");
            myRef.setValue(steps);
        }catch (Exception e){}
    }

    /*static void new_friend(){
        try {
            userID = fAuth.getCurrentUser().getUid();
            myRef = database.getReference("users").child(userID).child("new_friend");
            myRef.setValue("temp");
        }catch (Exception e){}
    }*/

    static void notifyAddFriend_f(){
        try {
            userID = fAuth.getCurrentUser().getUid();
            myRef = database.getReference("users").child(userID).child("addfriend_notif");
            myRef.setValue("false");
        }catch (Exception e){}
    }

    static void notifyAddFriend_t(String id){
        try {
            //userID = fAuth.getCurrentUser().getUid();
           myRef = database.getReference("users").child(id).child("addfriend_notif");
           myRef.setValue("true");

        }catch (Exception e){}
    }

    static void handled(){
        try {
            userID = fAuth.getCurrentUser().getUid();
            myRef = database.getReference("users").child(userID).child("handled");
            myRef.setValue("true");
        }catch (Exception e){}
    }
    static void handlednull(){
        try {
            userID = fAuth.getCurrentUser().getUid();
            myRef = database.getReference("users").child(userID).child("handled");
            myRef.setValue("null");
        }catch (Exception e){}
    }

    static void winnerAssignedF(String id){
        try {
        myRef = database.getReference("users").child(id).child("winner_assigned");
        myRef.setValue("false");
        }catch (Exception e){}
    }
    static void winnerAssignedF_init(){
        try {
            userID = fAuth.getCurrentUser().getUid();
            myRef = database.getReference("users").child(userID).child("winner_assigned");
            myRef.setValue("false");
        }catch (Exception e){}
    }

    static void winnerAssignedT(String id){
        try {
            myRef = database.getReference("users").child(id).child("winner_assigned");
            myRef.setValue("true");
        }catch (Exception ignored){}
    }

    static void play(){
        try {
            userID = fAuth.getCurrentUser().getUid();
            myRef = database.getReference("users").child(userID).child("play");
            myRef.setValue("true");


        }catch (Exception e){}
    }

    static void playOver(){
        try {
            userID = fAuth.getCurrentUser().getUid();
            myRef = database.getReference("users").child(userID).child("play");
            myRef.setValue("false");

            ArrayList<String> ids = new ArrayList<>();
            ArrayList<String> fids = new ArrayList<>();
            yourRef = database.getReference().child("users");
            fRef = database.getReference().child("users");
            yourRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                   // if(snapshot.child(userID).child("friends").exists()) {
                        int i = 0;
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            ids.add(dataSnapshot.getKey());
                            // System.out.println(snapshot.child(ids.get(i)).child("Daily_steps").getValue().toString());
                           /* for (DataSnapshot dataSnapshot2 : snapshot.child(userID).child("friends").getChildren()) {
                                //System.out.println(dataSnapshot2.getValue().toString());
                                if (dataSnapshot2.getValue().toString().equals(snapshot.child(ids.get(i)).child("username").getValue().toString()) && snapshot.child(ids.get(i)).child("in_league").getValue().toString().equals("true")) {
                                    fids.add(ids.get(i));
                                }
                            }*/
                            i++;
                        }

                        Map<String, Object> childUpdates = new HashMap<>();
                        for (int l = 0; l < ids.size(); l++) {
                            if(snapshot.child("in_league").equals("true")) {
                                //System.out.println(snapshot.child(fids.get(l)).child(list.get(k).getUsername()).exists());
                                //  if(snapshot.child(fids.get(l)).child("username").getValue().toString() == list.get(k).getUsername()) {
                                childUpdates.put(ids.get(l) + "/play", "false");
                            }
                        }
                        fRef.updateChildren(childUpdates);
                  //  }
                        }



                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){}
    }

    static void inLeague(){
        try {
            userID = fAuth.getCurrentUser().getUid();
            myRef = database.getReference("users").child(userID).child("in_league");
            myRef.setValue("true");
        }catch (Exception e){}
    }

    static void notInLeague(){
        try {
            userID = fAuth.getCurrentUser().getUid();
            myRef = database.getReference("users").child(userID).child("in_league");
            myRef.setValue("false");
            ArrayList<String> ids = new ArrayList<>();
            ArrayList<String> fids = new ArrayList<>();
            yourRef = database.getReference().child("users");
            fRef = database.getReference().child("users");
            yourRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // if(snapshot.child(userID).child("friends").exists()){
                    int i = 0;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ids.add(dataSnapshot.getKey());
                        // System.out.println(snapshot.child(ids.get(i)).child("Daily_steps").getValue().toString());

                       /* for (DataSnapshot dataSnapshot2 : snapshot.child(userID).child("friends").getChildren()) {
                            //System.out.println(dataSnapshot2.getValue().toString());
                            if (dataSnapshot2.getValue().toString().equals(snapshot.child(ids.get(i)).child("username").getValue().toString()) && snapshot.child(ids.get(i)).child("in_league").getValue().toString().equals("true")) {
                                fids.add(ids.get(i));
                            }
                        }*/
                        i++;
                    }

                    Map<String, Object> childUpdates = new HashMap<>();
                    for (int l = 0; l < ids.size(); l++) {
                        //System.out.println(snapshot.child(fids.get(l)).child(list.get(k).getUsername()).exists());
                        //  if(snapshot.child(fids.get(l)).child("username").getValue().toString() == list.get(k).getUsername()) {
                        childUpdates.put(ids.get(l) + "/in_league", "false");


                    }
                   fRef.updateChildren(childUpdates);
               // }
                }



                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }catch (Exception e){}
    }

    static void handled_f(){
        try {
            userID = fAuth.getCurrentUser().getUid();
            myRef = database.getReference("users").child(userID).child("handled");
            myRef.setValue("false");
            ArrayList<String> ids = new ArrayList<>();
            ArrayList<String> fids = new ArrayList<>();
            fRef = database.getReference().child("users");
            yourRef = database.getReference().child("users");
            yourRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                   // if(snapshot.child(userID).child("friends").exists()){
                        int i = 0;
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            ids.add(dataSnapshot.getKey());
                            // System.out.println(snapshot.child(ids.get(i)).child("Daily_steps").getValue().toString());

                            /*for (DataSnapshot dataSnapshot2 : snapshot.child(userID).child("friends").getChildren()) {
                                //System.out.println(dataSnapshot2.getValue().toString());
                                if (dataSnapshot2.getValue().toString().equals(snapshot.child(ids.get(i)).child("username").getValue().toString()) && snapshot.child(ids.get(i)).child("in_league").getValue().toString().equals("true")) {
                                    fids.add(ids.get(i));
                                }
                            }*/
                            i++;
                        }

                        Map<String, Object> childUpdates = new HashMap<>();
                        for (int l = 0; l < ids.size(); l++) {
                            //System.out.println(snapshot.child(fids.get(l)).child(list.get(k).getUsername()).exists());
                            //  if(snapshot.child(fids.get(l)).child("username").getValue().toString() == list.get(k).getUsername()) {
                            childUpdates.put(ids.get(l) + "/handled", "false");


                        }
                        fRef.updateChildren(childUpdates);
                    //}
                }



                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }catch (Exception e){}
    }

    static void setStepsTarget(String s){
        try {
            userID = fAuth.getCurrentUser().getUid();

            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child("Daily_Goal").exists()){

                    }else{
                        myRef = database.getReference("users").child(userID).child("target_days");
                        myRef.setValue(0);
                        myRef = database.getReference("users").child(userID).child("targets_met");
                        myRef.setValue(0);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            myRef = database.getReference("users").child(userID).child("Daily_Goal");
            myRef.setValue(s);
        }catch (Exception e){}
    }

    static void targetDaysPlus(int target){
        userID = fAuth.getCurrentUser().getUid();
        fRef = database.getReference("users").child(userID).child("target_days");
        int days = target+1;
        fRef.setValue(days);
    }
    static void consistencyPlus(int target){
        userID = fAuth.getCurrentUser().getUid();
        myRef = database.getReference("users").child(userID).child("target_met");
        int days = target+1;
        myRef.setValue(days);
    }

    static void setCurrFriend(String username){
        ArrayList<String> ids = new ArrayList<>();
        userID = fAuth.getCurrentUser().getUid();
        myRef2 = database2.getReference("users").child(userID).child("current_Friend");
        yourRef = database.getReference().child("users");
        yourRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ids.add(dataSnapshot.getKey());
                    // System.out.println(snapshot.child(ids.get(i)).child("Daily_steps").getValue().toString());
                    //System.out.println("runnung");
                    if(snapshot.child(ids.get(i)).child("username").getValue().toString().equals(username)){
                        //System.out.println("exists");
                        myRef2.setValue(ids.get(i));
                        break;
                    }else {
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    static void getNewFriend(String username){
        ArrayList<String> ids = new ArrayList<>();
        userID = fAuth.getCurrentUser().getUid();
        myRef2 = database2.getReference("users").child(userID).child("new_friend");
        yourRef = database.getReference().child("users");
        yourRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ids.add(dataSnapshot.getKey());
                    // System.out.println(snapshot.child(ids.get(i)).child("Daily_steps").getValue().toString());
                    //System.out.println("runnung");
                    if(snapshot.child(ids.get(i)).child("username").getValue().toString().equals(username)){
                        //System.out.println("exists");
                        myRef2.setValue(ids.get(i));
                        break;
                    }else {
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    static void displayfriendf(){
        userID = fAuth.getCurrentUser().getUid();
        myRef = database.getReference("users").child(userID).child("disp_friend");
        myRef.setValue("false");
    }
    static void displayfriendt(){
        userID = fAuth.getCurrentUser().getUid();
        myRef = database.getReference("users").child(userID).child("disp_friend");
        myRef.setValue("true");
    }

    static void getCurrentLeaguePos(String id){

        ArrayList<String> fids = new ArrayList<>();
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<PositionModel> list= new ArrayList<>();
        ArrayList<PositionModel> dlist= new ArrayList<>();
        yourRef = database.getReference().child("users");
        yourRef.addListenerForSingleValueEvent(new ValueEventListener() {

            int i =0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ids.add(dataSnapshot.getKey());
                    // System.out.println(snapshot.child(ids.get(i)).child("Daily_steps").getValue().toString());
                    for(DataSnapshot dataSnapshot2 : snapshot.child(id).child("friends").getChildren()) {
                        //System.out.println(dataSnapshot2.getValue().toString());
                        if(dataSnapshot2.getValue().toString().equals(snapshot.child(ids.get(i)).child("username").getValue().toString()) && snapshot.child(ids.get(i)).child("in_league").getValue().toString().equals("true")) {
                            fids.add(ids.get(i));

                            //System.out.println(snapshot.child(ids.get(i)).child("username").getValue().toString());

                            PositionModel model = new PositionModel("0", snapshot.child(ids.get(i)).child("username").getValue().toString(), snapshot.child(id).child("days").getValue().toString(),
                                    snapshot.child(ids.get(i)).child("Daily_steps").getValue().toString(), snapshot.child(ids.get(i)).child("Total_steps").getValue().toString());
                            dlist.add(model);

                        }

                    }
                    i++;

                }
                dlist.add(new PositionModel("0",snapshot.child(id).child("username").getValue().toString(),snapshot.child(id).child("days").getValue().toString(),
                        snapshot.child(id).child("Daily_steps").getValue().toString(),snapshot.child(id).child("Total_steps").getValue().toString()));

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
                fids.add(id);

               /* Map<String, Object> childUpdates = new HashMap<>();
                for(int k=0;k<list.size();k++){
                    for(int l=0;l<fids.size();l++){
                        //System.out.println(snapshot.child(fids.get(l)).child(list.get(k).getUsername()).exists());
                        if(snapshot.child(fids.get(l)).child("username").getValue().toString() == list.get(k).getUsername()) {
                            childUpdates.put(fids.get(l) + "/league_position", list.get(k).getPosition());


                        }
                    }

                }*/
               // yourRef.updateChildren(childUpdates);
               myRef = database.getReference("users").child(id).child("league_position");
                for(int k=0;k<list.size();k++) {
                    if(list.get(k).getUsername().equals(snapshot.child(id).child("username").getValue().toString()))
                    myRef.setValue(list.get(k).getPosition());
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    static void getWinner(String id){
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<String> fids = new ArrayList<>();
        yourRef = database.getReference().child("users");
        yourRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i=0;
                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ids.add(dataSnapshot.getKey());
                    // System.out.println(snapshot.child(ids.get(i)).child("Daily_steps").getValue().toString());
                    for(DataSnapshot dataSnapshot2 : snapshot.child(id).child("friends").getChildren()) {
                        //System.out.println(dataSnapshot2.getValue().toString());
                        if(dataSnapshot2.getValue().toString().equals(snapshot.child(ids.get(i)).child("username").getValue().toString())) {
                            fids.add(ids.get(i));
                        }

                    }
                    i++;
                }
                //userID = fAuth.getCurrentUser().getUid();
                fids.add(id);
               for(int l=0;l<fids.size();l++){
                    //System.out.println(snapshot.child(fids.get(l)).child("username");
                    //System.out.println(snapshot.child(fids.get(l)).child("league_position").getValue().toString());
                    if(snapshot.child(fids.get(l)).child("league_position").getValue().toString().equals("1")) {
                        int prev_titles = Integer.parseInt(snapshot.child(fids.get(l)).child("titles").getValue().toString());
                        System.out.println("entered");
                        if(snapshot.child(fids.get(l)).child("winner_assigned").getValue().toString().equals("false")) {
                            fRef = fdatabase.getReference("users").child(fids.get(l)).child("titles");
                            int currtitles = prev_titles + 1;
                            fRef.setValue(currtitles);
                            winnerAssignedT(fids.get(l));
                       }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    static void updateTotalSteps(int steps){
        userID = fAuth.getCurrentUser().getUid();
        myRef = database.getReference("users").child(userID).child("Total_steps");
        myRef.setValue(steps);
    }

    static void unaddFriend(String username){
        userID = fAuth.getCurrentUser().getUid();
        myRef = database.getReference("users").child(userID).child("friends").child(username);
        myRef.removeValue();

        fRef = fdatabase.getReference("users");
        fRef.addValueEventListener(new ValueEventListener() {
            ArrayList<String> ids = new ArrayList<>();
            int i=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name=snapshot.child(userID).child("username").getValue().toString();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ids.add(dataSnapshot.getKey());


                    System.out.println(dataSnapshot.getKey()+snapshot.child(ids.get(i)).child("username").getValue().toString());
                    try{
                        if(snapshot.child(ids.get(i)).child("username").getValue().toString().equals(username)) {
                            //System.out.println(username +"true");
                            yourRef = yourdatabase.getReference("users").child(ids.get(i)).child("friends").child(name);
                            yourRef.removeValue();

                        }else{
                            //i++;
                        }

                    } catch(Exception e) {
                        System.out.println("catch");
                        //i++;
                    }
                    i++;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    static void addFriend(String username){
        userID = fAuth.getCurrentUser().getUid();
        myRef = database.getReference("users").child(userID).child("friends").child(username);
        myRef.setValue(username);

        fRef = fdatabase.getReference("users");
        fRef.addValueEventListener(new ValueEventListener() {
            ArrayList<String> ids = new ArrayList<>();
            int i=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name=snapshot.child(userID).child("username").getValue().toString();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ids.add(dataSnapshot.getKey());


                    System.out.println(dataSnapshot.getKey()+snapshot.child(ids.get(i)).child("username").getValue().toString());
                    try{
                        if(snapshot.child(ids.get(i)).child("username").getValue().toString().equals(username)) {
                            //System.out.println(username +"true");
                            yourRef = yourdatabase.getReference("users").child(ids.get(i)).child("friends").child(name);
                            yourRef.setValue(name);
                            notifyAddFriend_t(ids.get(i));
                        }else{
                            //i++;
                        }

                    } catch(Exception e) {
                        System.out.println("catch");
                        //i++;
                    }
                    i++;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    static String getUsername(String email){
        int index = email.indexOf('.');
        String dummy = email.substring(0,index);

        String username = dummy.replace("@","");
        return  username;
    }

    static void writeUserName(String username){
        userID = fAuth.getCurrentUser().getUid();
        myRef = database.getReference("users").child(userID).child("username");
        myRef.setValue(username);

    }

    static void setHigheststep(int x){
        userID = fAuth.getCurrentUser().getUid();
        myRef = database.getReference("users").child(userID).child("step_highscore");
        myRef.setValue(x);
    }
    static void ini_targetDays(){
        userID = fAuth.getCurrentUser().getUid();
        myRef = database.getReference("users").child(userID).child("target_days");
        myRef.setValue("0");
    }
    static void ini_targetsMet(){
        userID = fAuth.getCurrentUser().getUid();
        myRef = database.getReference("users").child(userID).child("targets_met");
        myRef.setValue("0");
    }
    static void ini_dailyGoal(){
        userID = fAuth.getCurrentUser().getUid();
        myRef = database.getReference("users").child(userID).child("Daily_Goal");
        myRef.setValue("50");
    }

    static void writeDate(){
        Calendar calendar = Calendar.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        myRef = database.getReference("users").child(userID).child("Date");
        myRef.setValue(calendar);

    }
/*
    static void stepFunction(){
        DatabaseReference ref;
        userID = fAuth.getCurrentUser().getUid();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        ref = db.getReference("users").child(userID);
        ref.addValueEventListener(new ValueEventListener() {
            public Integer steps=0;
            private double old_Magnitude = 0;


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Calendar calendar = Calendar.getInstance();
                int day = Integer.parseInt(snapshot.child("Date").child("time").child("day").getValue().toString());
                int month = Integer.parseInt(snapshot.child("Date").child("time").child("month").getValue().toString());
                int prev_Tsteps = Integer.parseInt(snapshot.child("Total_steps").getValue().toString());

                if(day != (calendar.get(calendar.DAY_OF_WEEK)-1) && month == calendar.get(Calendar.MONTH)){

                    String s = Objects.requireNonNull(snapshot.child("Daily_steps").getValue()).toString();
                    int totalSteps = prev_Tsteps+Integer.parseInt(s);
                    updateTotalSteps(totalSteps);
                    steps = 0;
                    writeDate();

                }else if (month != calendar.get(Calendar.MONTH)) {
                    updateTotalSteps(0);
                    steps = 0;
                    writeDate();

                }else{
                    String s = Objects.requireNonNull(snapshot.child("Daily_steps").getValue()).toString();
                    steps = Integer.parseInt(s);
                }
                Context c = null;
                SensorManager sensorManager = (SensorManager) c.getSystemService(Context.SENSOR_SERVICE);
                Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);// access and use accelerometer

                SensorEventListener stepDetector = new SensorEventListener() {
                    @Override
            /*
            sensor event
            */
/*
                    public void onSensorChanged(SensorEvent event) {
                        if (event != null) {
                            float x = event.values[0];
                            float y = event.values[1];
                            float z = event.values[2];
                            double magnitude = Math.sqrt(x * x + y * y + z * z);
                            double difference = magnitude - old_Magnitude;
                            old_Magnitude = magnitude;

                            if (difference > 5)
                                steps++;

                           // stepCount.setText(steps.toString());
                            // myRef.setValue(steps);
                            updateSteps(steps);
                        }
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {

                    }

                };
                sensorManager.registerListener(stepDetector, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
*/

    //get fire base authentication  instance
    static void getAuthInstance(){
        fAuth = FirebaseAuth.getInstance();
    }
    //check if user logged in
    static boolean checkUser(){
        if(fAuth.getCurrentUser() !=null)
            return true;

        else return false;
    }
    //logout user
    static void logOut(){
        FirebaseAuth.getInstance().signOut();
    }

}
