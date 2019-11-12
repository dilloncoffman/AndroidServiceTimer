package edu.temple.service_11_12_19;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class TimerService extends IntentService {
    public TimerService() {
        super("TimerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) { // Intent that's received here is the same intent that's created to start the Service, which means you can pass extra data with it
        int from = intent.getIntExtra("from", 20);

        for (int i = from; i >= 0; i--) {
            Log.d("Countdown", i + "");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
