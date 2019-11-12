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

        for (int i = from; i >= 0; i--) {
            Log.d("Countdown", i + "");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Put info you want recipient Activity to get from Broadcast inside Intent
            countdownIntent = new Intent(getPackageName() + ".COUNTDOWN_ACTION"); // DO THIS: Prefix that intent action with your package to make it unique, because once you have that, the action itself might not be unique, could be an action another developer from another app thought of
            countdownIntent.putExtra("countdown", i);
            // Send Broadcast - anything broadcast this way can be received by any component on the device, not just your app
            sendBroadcast(countdownIntent); // Context method so available in any Service or Activity
            // Make Intent unambiguous, allow client to listen for this intent specifically using an intent-filter, give Intent information that will make it unique, to do this we give Intent an action

            // Use local broadcast manager to send broadcast to your app's components - PREFERRED
//            LocalBroadcastManager.getInstance(this) // Narrows scope, goes to components within your app, make sure wrong component doesn't get the message
//                    .sendBroadcast();
        }
    }
}
