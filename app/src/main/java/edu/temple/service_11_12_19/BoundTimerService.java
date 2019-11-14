package edu.temple.service_11_12_19;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BoundTimerService extends Service {

    // Present some functionality to a client using a Binder, methods that are exposed to client
    class TimerBinder extends Binder {
        // Define set of methods that other clients will see when they connect to Service
        public void startShortTimer() {
            startTimer(10);
        }

        public void startMediumTimer() {
            startTimer(30);
        }

        public void startLongTimer() {
            startTimer(60);
        }
    }

    public BoundTimerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new TimerBinder(); // whenever a client binds to Service, then TimerBinder is returned
    }

    // Define functionality, methods needed for Service
    private void startTimer(int from) {
        new Thread () { // run Service in worker thread, unless using IntentService that spins one up for you
            @Override
            public void run() {
                for (int i = from; i >= 0; i--) {
                    Log.d("Countdown", i + "");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
