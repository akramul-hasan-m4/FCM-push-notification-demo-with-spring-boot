package com.spring.pushnotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btngo = findViewById(R.id.btngo);
        Button btnfragment = findViewById(R.id.btnfragment);
        btngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(t);
            }
        });
        fragmentManager = getSupportFragmentManager();

        String type = getIntent().getStringExtra("From");
        if (type != null) {
            openFragment();
        }
        btnfragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment();
            }
        });
        initilizeFCM();
    }

    private void openFragment(){
        fragmentManager.beginTransaction().replace(R.id.main_fragment, new BlankFragment()).addToBackStack(null).commit();
    }

    private void initilizeFCM(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("MyNotifications", "MyNotifications",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        //FirebaseMessaging.getInstance().subscribeToTopic("NEWS");  or
        FirebaseMessaging.getInstance().subscribeToTopic("NEWS")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // String msg = getString(R.string.msg_subscribed);
                        String msg = "successfull";
                        if (!task.isSuccessful()) {
                            msg = "failed";
                        }

                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
