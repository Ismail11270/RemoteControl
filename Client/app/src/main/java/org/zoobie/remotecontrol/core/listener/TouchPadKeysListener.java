package org.zoobie.remotecontrol.core.listener;

import android.util.Log;
import android.view.View;

import org.zoobie.remotecontrol.core.actions.Actions;
import org.zoobie.remotecontrol.core.connection.ClientUdp;
import org.zoobie.remotecontrol.core.connection.Server;


public class TouchPadKeysListener implements View.OnClickListener {
    private final String TAG = "MousePadController";
    ClientUdp client;
    public TouchPadKeysListener(Server server){
        client = new ClientUdp(server);
    }

    @Override
    public void onClick(View v) {
        int keyCode = Integer.parseInt(v.getTag().toString());
        Log.d(TAG,"Key " + keyCode);
        client.sendData(Actions.MOUSE_KEY_ACTION + keyCode + "");
    }

}
