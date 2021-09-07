package tech.abdullah.watersensor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Random;

public class WaterRateActivity extends AppCompatActivity {


    private TextView t1, t2, t3, t4, t5, t6;
    private String noti = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_rate);


        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);
        t5 = findViewById(R.id.t5);
        t6 = findViewById(R.id.t6);


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

// Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value1, value2, value3, value4, value5, value6;
                value1 = String.valueOf(dataSnapshot.child("limit").getValue());
                value2 = String.valueOf(dataSnapshot.child("flowrate").getValue());
                value3 = String.valueOf(dataSnapshot.child("outputLiquidCurrent").getValue());
                value4 = String.valueOf(dataSnapshot.child("outputLiquidQuantity").getValue());
                value5 = String.valueOf(dataSnapshot.child("totalMilliLitres").getValue());
                value6 = String.valueOf(dataSnapshot.child("price").getValue());

                Log.d("TAG", "Value1 is: " + value1);
                Log.d("TAG", "Value2 is: " + value2);
                Log.d("TAG", "Value3 is: " + value3);
                Log.d("TAG", "Value4 is: " + value4);
                Log.d("TAG", "Value5 is: " + value5);
                Log.d("TAG", "Value6 is: " + value6);

                t1.setText(value3);
                t2.setText(value4);
                t3.setText(value2);
                t4.setText(value5);
                t5.setText(value6);

                if (value1.equals("true")) {
                    noti = "تم التجاوز";
                    t6.setText("تم التجاوز");
                    notification();
                }else {

                    noti = "لم يتم التجاوز";
                    t6.setText("لم يتم التجاوز");

                }





            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }


        });


        DatabaseReference myRef5 = database.getReference().child("limit");
        myRef5.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                notification();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                notification();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                notification();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                notification();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void notification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel("Notification","Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager)getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,"Notification")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("تم تجاوز الإستهلاك")
                    .setContentText(noti)
                    .setAutoCancel(true)
                    .setContentInfo("Info");

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
            managerCompat.notify(new Random().nextInt(),notificationBuilder.build());


        }

    }
}