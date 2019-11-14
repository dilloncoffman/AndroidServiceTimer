package edu.temple.service_11_12_19;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class TimerService extends IntentService {
    public TimerService() {
        super("TimerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) { // Intent that's received here is the same intent that's created to start the Service, which means you can pass extra data with it
        int from = intent.getIntExtra("from", 20); // cannot now update this information, IntentService means requests are independent and IntentService will queue additional results, so can't tell timer to pause

        // Send a Broadcast to Activity with Service status using intent
        Intent countdownIntent;

    }
}
