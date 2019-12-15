package org.zoobie.remotecontrol.core.listener;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.zoobie.remotecontrol.core.actions.Actions;
import org.zoobie.remotecontrol.core.connection.Connector;

public class TouchPadGestureListener implements View.OnTouchListener, GestureDetector.OnDoubleTapListener, GestureDetector.OnGestureListener {
    private Context context;
    private GestureDetector gestureDetector;
    private Connector connector;
    private float sens;

    public TouchPadGestureListener(Context context, Connector connector) {
        this.context = context;
        this.connector = connector;
        gestureDetector = new GestureDetector(context,this);
        gestureDetector.setOnDoubleTapListener(this);
    }

    public GestureDetector getGestureDetector(){
        return gestureDetector;
    }

    private final String TAG = "TouchPadListener";

    @Override
    public boolean onDown(MotionEvent e) {
//        Log.i(TAG,"ON DOWN");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
//        Log.i(TAG,"ON SHOW PRESS");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
//        Log.i(TAG,"ON SINGLE TAP UP");
        return true;
    }

    /**
     * todo
     * get sens from settings
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.i(TAG,"ON SCROLL " + distanceX + " " + distanceY);
        int x = (int) (distanceX*sens), y = (int) (distanceY*sens);
        connector.send(Actions.MOUSE_MOVE_ACTION,(byte)(x),(byte)(y));
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.i(TAG,"ON LONG PRESS");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i(TAG,"ON FLING");
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.i(TAG,"Double tap");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
//        client.send(event.getX() + "," + event.getY());
//        Log.i(TAG,"ON TOUCH");
        return true;
    }

    public void setSens(float sens) {
        this.sens = sens;
    }
}
