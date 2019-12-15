package org.zoobie.remotecontrol.core.listener;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


import org.zoobie.remotecontrol.core.connection.Connector;
import org.zoobie.remotecontrol.core.connection.Server;

public class TouchPadListener implements View.OnClickListener, View.OnTouchListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private Context ctx;
    private Connector connector;
    private final String TAG = "TouchPadListener";

    public TouchPadListener(Context ctx, Connector connector){
        this.ctx = ctx;
        this.connector = connector;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.i(TAG,"ON DOWN");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.i(TAG,"ON SHOW PRESS");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.i(TAG,"ON SINGLE TAP UP");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.i(TAG,"ON SCROLL");

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.i(TAG,"ON LONG PRESS");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i(TAG,"ON FLING");
        return false;
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG,"ON CLICK");
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        client.send(event.getX() + "," + event.getY());
        Log.i(TAG,"ON TOUCH");
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.i(TAG,"Double tap");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.i(TAG,"Double tap");
        return false;
    }
}
