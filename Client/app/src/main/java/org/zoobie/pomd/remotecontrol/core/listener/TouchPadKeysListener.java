package org.zoobie.pomd.remotecontrol.core.listener;

import android.util.Log;
import android.view.View;
import org.zoobie.pomd.remotecontrol.core.commands.KeyAction;
import org.zoobie.pomd.remotecontrol.core.controller.TouchPadController;

import java.util.List;


public class TouchPadKeysListener implements View.OnClickListener {
    private TouchPadController controller;
    private final String TAG = "MousePadController";
    public TouchPadKeysListener(TouchPadController touchPadController){
        this.controller = touchPadController;
    }

    @Override
    public void onClick(View v) {
        int keyCode = Integer.parseInt(v.getTag().toString());
        Log.d(TAG,"Key " + keyCode);
        controller.sendMouseAction(keyCode);
    }

}
