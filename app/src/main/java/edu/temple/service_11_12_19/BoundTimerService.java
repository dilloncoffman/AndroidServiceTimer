package edu.temple.service_11_12_19;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class BoundTimerService extends Service {

    Handler timerHandler; // reference to client Handler, for Service to communicate with client
    boolean paused;

    // Present some functionality to a client using a Binder, methods that are exposed to client
    class TimerBinder extends Binder {
        // Define set of methods that other clients will see when they connect to Service

        public void startShortTimer(Handler handler) {
            BoundTimerService.this.timerHandler = handler;
            startTimer(10);
        }

        public void startMediumTimer(Handler handler) {
            BoundTimerService.this.timerHandler = handler;
            startTimer(30);
        }

        public void startLongTimer(Handler handler) {
            BoundTimerService.this.timerHandler = handler;
            startTimer(60);
        }

        public void pause() { // allow user to toggle between paused and play
            paused = !paused;
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
                    timerHandler.sendEmptyMessage(i); // send Handler empty message
                    while(paused); // spin-lock for pausing, not efficient but just to show
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
