package org.zoobie.remotecontrol.core.listener;

import android.util.Log;
import android.view.View;

import org.zoobie.remotecontrol.core.actions.Actions;
import org.zoobie.remotecontrol.core.connection.Connector;


public class TouchPadKeysListener implements View.OnClickListener {
    private final String TAG = "MousePadController";
    private Connector connector;
    public TouchPadKeysListener(Connector connector){
        this.connector = connector;
    }

    @Override
    public void onClick(View v) {
        int keyCode = Integer.parseInt(v.getTag().toString());
        Log.d(TAG,"Key " + keyCode);
        byte[] bytes = new byte[] {Actions.MOUSE_KEY_CLICK, (byte)keyCode};
        connector.sendUdp(bytes);
    }



}
