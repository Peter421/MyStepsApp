package com.example.mystepsapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import static com.example.mystepsapp.Singleton.consistencyPlus;
import static com.example.mystepsapp.Singleton.database;
import static com.example.mystepsapp.Singleton.fAuth;
import static com.example.mystepsapp.Singleton.getCurrentLeaguePos;
import static com.example.mystepsapp.Singleton.getWinner;
import static com.example.mystepsapp.Singleton.handled;
import static com.example.mystepsapp.Singleton.handled_f;
import static com.example.mystepsapp.Singleton.logOut;
import static com.example.mystepsapp.Singleton.notInLeague;
import static com.example.mystepsapp.Singleton.notifyAddFriend_f;
import static com.example.mystepsapp.Singleton.playOver;
import static com.example.mystepsapp.Singleton.setHigheststep;
import static com.example.mystepsapp.Singleton.targetDaysPlus;
import static com.example.mystepsapp.Singleton.updateSteps;
import static com.example.mystepsapp.Singleton.updateTotalSteps;
import static com.example.mystepsapp.Singleton.userID;
import static com.example.mystepsapp.Singleton.writeDate;

public class MainActivity extends AppCompatActivity {

    private TextView stepCount,position,stepsRemaining;
    private double old_Magnitude = 0;
    public Integer steps=0;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref;
    DatabaseReference ref2;
    FirebaseDatabase db2 = FirebaseDatabase.getInstance();
    Button friends,table,myprofile;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(fAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), Register.class));
            finish();
        }

        friends = findViewById(R.id.friends_page);
        table = findViewById(R.id.table);
        stepCount = findViewById(R.id.stepCount);
        stepsRemaining = findViewById(R.id.stepstogaol);
        position =  findViewById(R.id.position_);
        myprofile = findViewById(R.id.my_profile);

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("channel","notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager nm = getSystemService(NotificationManager.class);
            nm.createNotificationChannel(channel);
        }
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel channel2 = new NotificationChannel("channel2","notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager nm2 = getSystemService(NotificationManager.class);
            nm2.createNotificationChannel(channel2);
        }
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel channel3 = new NotificationChannel("channel3","notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager nm3 = getSystemService(NotificationManager.class);
            nm3.createNotificationChannel(channel3);
        }
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel channel4 = new NotificationChannel("channel4","notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager nm4 = getSystemService(NotificationManager.class);
            nm4.createNotificationChannel(channel4);
        }
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel channel5 = new NotificationChannel("channel5","notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager nm5 = getSystemService(NotificationManager.class);
            nm5.createNotificationChannel(channel5);
        }
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel channel6 = new NotificationChannel("channel6","notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager nm6 = getSystemService(NotificationManager.class);
            nm6.createNotificationChannel(channel6);
        }
       // writeDate();
       // ini_targetDays();ini_targetsMet();
        //ini_dailyGoal();



        userID = fAuth.getCurrentUser().getUid();
        ref2 = database.getReference().child("users");
        ref = db.getReference("users").child(userID);
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                position.setText("League Position: \n \n" +snapshot.child("league_position").getValue().toString());




                int i =0;
                Calendar calendar = Calendar.getInstance();

                try {
                    int day = Integer.parseInt(snapshot.child("Date").child("time").child("day").getValue().toString());

                int nxtdaycount=1;
                if(snapshot.child("days").exists()) {
                     nxtdaycount = Integer.parseInt(snapshot.child("days").getValue().toString()) + 1;}
                int month = Integer.parseInt(snapshot.child("Date").child("time").child("month").getValue().toString());
                int prev_Tsteps = Integer.parseInt(snapshot.child("Total_steps").getValue().toString());
                int highscore =  Integer.parseInt(snapshot.child("step_highscore").getValue().toString());

                int targetdays = Integer.parseInt(snapshot.child("target_days").getValue().toString());
                int targetsmet =  Integer.parseInt(snapshot.child("targets_met").getValue().toString());
                int dailyGoal = Integer.parseInt(snapshot.child("Daily_Goal").getValue().toString());

                String dailySteps = Objects.requireNonNull(snapshot.child("Daily_steps").getValue()).toString();
                String inLeague = snapshot.child("in_league").getValue().toString();
                String play = snapshot.child("play").getValue().toString();
                String handled = snapshot.child("handled").getValue().toString();

                if(play.equals("false") && inLeague.equals("false")){
                    startActivity(new Intent(getApplicationContext(),LeagueOver.class));
                    finish();
                }

                if(snapshot.child("addfriend_notif").getValue().toString().equals("true")){

                    NotificationCompat.Builder builder2 = new NotificationCompat.Builder(MainActivity.this,"channel2");
                    builder2.setContentTitle("NEW FRIEND");
                    builder2.setContentText("Congratulations you have new friend(s) to start competing with ;)");
                    builder2.setSmallIcon(R.drawable.ic_launcher_background);
                    builder2.setAutoCancel(true);
                    NotificationManagerCompat manager2 = NotificationManagerCompat.from(MainActivity.this);
                    manager2.notify(1,builder2.build());
                    notifyAddFriend_f();
                }
                if(Integer.parseInt(dailySteps) == dailyGoal/2){
                    NotificationCompat.Builder builder6 = new NotificationCompat.Builder(MainActivity.this,"channel6");
                    builder6.setContentTitle("Almost there");
                    builder6.setContentText("you are halfway to your daily steps goal");
                    builder6.setSmallIcon(R.drawable.ic_launcher_background);
                    builder6.setAutoCancel(true);
                    NotificationManagerCompat manager6 = NotificationManagerCompat.from(MainActivity.this);
                    manager6.notify(1,builder6.build());
                }


                if(day != (calendar.get(calendar.DAY_OF_WEEK)-1) && month == calendar.get(Calendar.MONTH)){
                    targetDaysPlus(targetdays);
                    if(day -(calendar.get(calendar.DAY_OF_WEEK)-1) > 2){
                        NotificationCompat.Builder builder3 = new NotificationCompat.Builder(MainActivity.this,"channel3");
                        builder3.setContentTitle("Welcome Back");
                        builder3.setContentText("it's been a while since you last logged on, glad to have you back buddy!");
                        builder3.setSmallIcon(R.drawable.ic_launcher_background);
                        builder3.setAutoCancel(true);
                        NotificationManagerCompat manager3 = NotificationManagerCompat.from(MainActivity.this);
                        manager3.notify(1,builder3.build());
                    }
                    //setDayCount(nxtdaycount);
                    if(Integer.parseInt(dailySteps) > highscore){
                        setHigheststep(Integer.parseInt(dailySteps));
                        NotificationCompat.Builder builder5 = new NotificationCompat.Builder(MainActivity.this,"channel5");
                        builder5.setContentTitle("Congratulations");
                        builder5.setContentText("You have reached a new steps high-score");
                        builder5.setSmallIcon(R.drawable.ic_launcher_background);
                        builder5.setAutoCancel(true);
                        NotificationManagerCompat manager5 = NotificationManagerCompat.from(MainActivity.this);
                        manager5.notify(1,builder5.build());
                    }
                    if(snapshot.child("in_league").getValue().toString().equals("true")){
                        NotificationCompat.Builder builder4 = new NotificationCompat.Builder(MainActivity.this,"channel4");
                        builder4.setContentTitle("Welcome Back");
                        builder4.setContentText("You start off the day as no."+snapshot.child("league_position").getValue().toString()+" in the league");
                        builder4.setSmallIcon(R.drawable.ic_launcher_background);
                        builder4.setAutoCancel(true);
                        NotificationManagerCompat manager4 = NotificationManagerCompat.from(MainActivity.this);
                        manager4.notify(1,builder4.build());
                    }
                    try {

                        if (Integer.parseInt(dailySteps) >= dailyGoal) {
                            consistencyPlus(targetsmet);
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,"channel");
                            builder.setContentTitle("Steps target Met");
                            builder.setContentText("Congratulations you met your daily steps target yesterday");
                            builder.setSmallIcon(R.drawable.ic_launcher_background);
                            builder.setAutoCancel(true);
                            NotificationManagerCompat manager = NotificationManagerCompat.from(MainActivity.this);
                            manager.notify(1,builder.build());
                        }
                    }catch (Exception e){}
                    int totalSteps = prev_Tsteps+Integer.parseInt(dailySteps);
                    updateTotalSteps(totalSteps);
                    steps = 0;
                    updateSteps(0);
                    writeDate();

                }else if (month != calendar.get(Calendar.MONTH)) {
System.out.println("ran 1");
                    try {
                        targetDaysPlus(targetdays);
                        if (Integer.parseInt(dailySteps) >= dailyGoal) {
                            consistencyPlus(targetsmet);
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this,"channel");
                            builder.setContentTitle("Steps target Met");
                            builder.setContentText("Congratulations you met your daily steps target yesteray");
                            builder.setSmallIcon(R.drawable.ic_launcher_background);
                            builder.setAutoCancel(true);
                            NotificationManagerCompat manager = NotificationManagerCompat.from(MainActivity.this);
                            manager.notify(1,builder.build());

                        }
                    }catch (Exception e){}
                    //boolean handled to be turned off/on when winner has been got
                    if(handled.equals("true")) {
                        ArrayList<String> ids = new ArrayList<>();
                        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    ids.add(dataSnapshot.getKey());
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        for(i=0;i<ids.size();i++) {
                            getCurrentLeaguePos(ids.get(i));
                            getWinner(ids.get(i));
                        }//if handled is false

                    }
                    if(inLeague.equals("true") && handled.equals("true")){
                        System.out.println("ran 2");
                        handled_f();
                        playOver();
                        notInLeague();
                    }
                    //set handled to true for everyone
                    updateTotalSteps(0);
                    if(Integer.parseInt(dailySteps) > highscore) {
                        setHigheststep(Integer.parseInt(dailySteps));
                    }
                    steps = 0;
                    updateSteps(0);
                    writeDate();
                    if(!snapshot.child("handled").getValue().toString().equals("null")) {
                        handled();
                    }


                }else {
                    //  String s = Objects.requireNonNull(snapshot.child("Daily_steps").getValue()).toString();

                    steps = Integer.parseInt(dailySteps);
                }
                SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
                Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);// access and use accelerometer

                SensorEventListener stepDetector = new SensorEventListener() {
                    @Override
            /*
            sensor event
            */

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

                            stepCount.setText("Daily Step Count: \n \n" +steps.toString());
                            stepsRemaining.setText("Steps to Daily Goal: \n \n" +String.valueOf(dailyGoal - steps) );
                            // myRef.setValue(steps);
                            updateSteps(steps);
                        }

                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int accuracy) {

                    }

                };
                sensorManager.registerListener(stepDetector, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
               }catch (Exception e){
                    logOut();
                    startActivity(new Intent(getApplicationContext(),Register.class));
                    finish();
                } }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),FriendInbetween.class));
                finish();

            }
        });

//comment added

        table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LeagueTable.class));
                finish();

            }
        });

        myprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),MyAchievments.class));
                startActivity(new Intent(getApplicationContext(),MyProfile.class));

                finish();

            }
        });


    }
}
