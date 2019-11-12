package edu.temple.service_11_12_19;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Programmatically create Broadcast to receive message from TimerService

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // ContentProviders and BroadcastReceivers are given a Context object that have some functionality but not everything an Activity and Service can do
            timerTextView.setText(String.valueOf(intent.getIntExtra("countdown", 0)));
        }
    };

    // BroadcastReceiver needs an Intent Filter
    IntentFilter filter;
    TextView timerTextView;

    // Need to register Receiver and Filter with Activity, usually done in onStart()
    @Override
    protected void onStart() {
        super.onStart();

        filter = new IntentFilter();
        filter.addAction(getPackageName() + ".COUNTDOWN_ACTION");
        // Register BroadcastReceiver and IntentFilter for this Activity
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);

        Intent intent = new Intent(this, TimerService.class);
        intent.putExtra("from", 15);

        findViewById(R.id.startTimerBtn).setOnClickListener(v -> {
            startService(intent);
        });
    }

    // Anything you do in one mirror method, you must do opposite in its mirror method, so onStart needs onStop to unregister receiver
    @Override
    protected void onStop() {
        super.onStop();

        // Unregister receiver, otherwise you have a memory leak
        unregisterReceiver(receiver);
    }
}
