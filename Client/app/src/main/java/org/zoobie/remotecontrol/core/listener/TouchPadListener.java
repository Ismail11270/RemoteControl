package org.zoobie.remotecontrol.core.listener;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.zoobie.remotecontrol.core.connection.ClientUdp;
import org.zoobie.remotecontrol.core.connection.Server;

public class TouchPadListener implements View.OnClickListener, View.OnTouchListener, GestureDetector.OnGestureListener {
    private Context ctx;
    private Server server;
    private ClientUdp client;
    private final String TAG = "TouchPadListener";

    public TouchPadListener(Context ctx, Server server){
        this.server = server;
        this.ctx = ctx;
        this.client = new ClientUdp(server);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        client.sendData(event.getX() + "," + event.getY());
        Log.i(TAG,"LONG PRESS DATA SENT");
        return true;
    }
}
