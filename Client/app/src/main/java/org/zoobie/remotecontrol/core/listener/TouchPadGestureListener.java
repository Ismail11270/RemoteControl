package org.zoobie.remotecontrol.core.listener;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import org.zoobie.remotecontrol.core.actions.Actions;
import org.zoobie.remotecontrol.core.connection.Connector;

public class TouchPadGestureListener implements View.OnTouchListener,  GestureDetector.OnGestureListener {
    private Context context;
    private GestureDetector gestureDetector;
    private Connector connector;
    private float sens;
    private int counter = 0;
    private final int ACCURACY = 0;

    public TouchPadGestureListener(Context context, Connector connector) {
        this.context = context;
        this.connector = connector;
        gestureDetector = new GestureDetector(context, this);
//        gestureDetector.setOnDoubleTapListener(this);
    }

    public GestureDetector getGestureDetector() {
        return gestureDetector;
    }

    private final String TAG = "TouchPadListener";

    @Override
    public boolean onDown(MotionEvent e) {
        Log.i(TAG, "ON DOWN");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.i(TAG, "ON SHOW PRESS");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        int keyCode = 1;
        byte[] bytes = new byte[]{Actions.MOUSE_KEY_ACTION, (byte) keyCode};
        connector.sendUdp(bytes);
        return true;
    }

    /**
     * todo
     * get sens from settings
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (counter < ACCURACY) {
            counter++;
        } else {
//            Toast.makeText(context, "SCROLL", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "ON SCROLL " + distanceX + " " + distanceY);
            int x = (int) (distanceX * sens), y = (int) (distanceY * sens);
            connector.send(Actions.MOUSE_MOVE_ACTION, (byte) (x), (byte) (y));
            counter = 0;
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
//        Log.i(TAG,"ON LONG PRESS");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        Log.i(TAG,"ON FLING");
        return true;
    }

//    @Override
//    public boolean onSingleTapConfirmed(MotionEvent e) {
////        Log.i(TAG,"SINGLE TAP CONFIRMED");
//        return true;
//    }
//
//    @Override
//    public boolean onDoubleTap(MotionEvent e) {
////        Log.i(TAG,"Double tap");
//        return true;
//    }
//
//    @Override
//    public boolean onDoubleTapEvent(MotionEvent e) {
//        return true;
//    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                System.out.println("UP");
                break;
            case MotionEvent.ACTION_DOWN:
                System.out.println("DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "MOVE");
                break;
        }
        gestureDetector.onTouchEvent(event);
        return true;
    }

    public void setSens(float sens) {
        this.sens = sens;
    }
}
