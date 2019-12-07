package org.zoobie.remotecontrol.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class DataProcessor extends Service {
    private final String TAG = "DataProcessor";
    public DataProcessor() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }

    @Override
    public void onCreate() {

        Log.i(TAG,"SERVICE STARTED");


    }


}
