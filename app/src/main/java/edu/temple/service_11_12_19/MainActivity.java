package edu.temple.service_11_12_19;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    boolean connected;
    BoundTimerService.TimerBinder binder; // since you know what Service will be returned

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // Receives component you're connected to and a Binder
            /*
                Things to do after connecting
                1. Check if you're connected
                2. Hold on to Binder that service is returning that describes interactions you can perform
                3. Now you can use this ServiceConnection in bindService(intent, serviceConnection, ...) below
             */
            connected = true;
            binder = (BoundTimerService.TimerBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // Component you just disconnected from
            connected = false;
            binder = null; // to protect against memory leak
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);

        Intent intent = new Intent(this, BoundTimerService.class);

        // 1st is intent,
        // 2nd is a ServiceConnection obj - job is to tell you when you're connected, disconnected,
        // and when you connect it gives you the Binder object the Service returns to you to interact with the service
        // 3rd is flag to tell Android how important Service is to developer/user
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE); // bind instead of start Service, but binding service takes 3 args,


        findViewById(R.id.startTimerBtn).setOnClickListener(v -> {

            // 1st because you're interacting with Bound service, make sure you're connected
            if (connected) {
                // now you can use binder to use methods exposed by Bound Service
                binder.startMediumTimer();
            }
        });

        findViewById(R.id.pauseButton).setOnClickListener(v -> {
            if (connected) { // if connected to bound service
                binder.pause();
            }
        });
    }
}
