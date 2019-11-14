package edu.temple.service_11_12_19;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class BoundTimerService extends Service {

    private Handler timerHandler; // reference to client Handler, for Service to communicate with client
    public final static String INTERFACE_TYPE = "interface_type";
    public final static int BASIC_INTERFACE = 0;
    public final static int ADVANCED_INTERFACE = 1;


    boolean paused;

    class AdvancedTimerBinder extends Binder {
        // create many Binder types
        BoundTimerService getService() {
            return BoundTimerService.this;
        }
    }

    // Present some functionality to a client using a Binder, methods that are exposed to client, client uses to indirectly speak to the Service, calls methods on binder which then calls Service information
    class BasicTimerBinder extends Binder {
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

        // ORRRRRRR
        // Returns running instance of TimerService, a reference to Service is handed over to Activity
//        BoundTimerService getService() {
//            return BoundTimerService.this;
//        }
    }

    public BoundTimerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Determine which Binder type to send, use Intent used to bind
        if (intent.getIntExtra(INTERFACE_TYPE, BASIC_INTERFACE) == 0)
            return new BasicTimerBinder(); // whenever a client binds to Service, then TimerBinder is returned
        else
            return new AdvancedTimerBinder();
    }

    // Define functionality, methods needed for Service
    public void startTimer(int from) {
        new Thread () { // run Service in worker thread, unless using IntentService that spins one up for you
            @Override
            public void run() {
                for (int i = from; i >= 0; i--) {
                    if (timerHandler != null) {
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
            }
        }.start();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // could set timerHandler = null;
        timerHandler = null;
        return super.onUnbind(intent);
    }
}
