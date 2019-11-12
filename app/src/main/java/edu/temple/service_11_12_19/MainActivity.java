package edu.temple.service_11_12_19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, TimerService.class);
        intent.putExtra("from", 15);

        findViewById(R.id.startTimerBtn).setOnClickListener(v -> {
            startService(intent);
        });
    }
}
