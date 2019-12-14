package org.zoobie.remotecontrol.core.listener;

import android.util.Log;
import android.view.View;

import org.zoobie.remotecontrol.core.actions.Actions;
import org.zoobie.remotecontrol.core.controller.ConnectionController;


public class TouchPadKeysListener implements View.OnClickListener {
    private final String TAG = "MousePadController";
    private ConnectionController controller;
    public TouchPadKeysListener(ConnectionController controller){
        this.controller = controller;
    }

    @Override
    public void onClick(View v) {
        int keyCode = Integer.parseInt(v.getTag().toString());
        Log.d(TAG,"Key " + keyCode);
        byte[] bytes = new byte[] {Actions.MOUSE_KEY_ACTION, (byte)keyCode};
        controller.sendUdp(bytes);
    }

}
