package org.zoobie.remotecontrol.core.listener;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.zoobie.remotecontrol.core.actions.Actions;
import org.zoobie.remotecontrol.core.connection.Connector;
import org.zoobie.remotecontrol.view.TouchpadButton;

public class TouchPadKeysGestureListener extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {
    private static final String TAG = "Touch_Pad_Keys_Listener";
    private Context context;
    private GestureDetector gestureDetector;
    private Connector connector;
    private View view;
    private TouchPadGestureListener touchpadListener;

    public TouchPadKeysGestureListener(Context context, Connector connector, TouchPadGestureListener touchpadListener) {
        this.context = context;
        this.connector = connector;
        gestureDetector = new GestureDetector(context, this);
        gestureDetector.setOnDoubleTapListener(this);
        this.touchpadListener = touchpadListener;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.performClick();
//        Log.i(TAG,"POINTER " + event.getPointerCount());
        view = v;
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "Action up");
                break;
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "Action down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, event.getPointerCount() + " Pointers found");
                break;
        }
        gestureDetector.onTouchEvent(event);
        return true;
    }


    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        TouchpadButton bt = (TouchpadButton) view;
        if (bt.isButtonPressed()) {
            bt.toggle();
            bt.buttonPress();
            sendCommand(view,Actions.MOUSE_UP_ACTION);
        } else sendCommand(view,Actions.MOUSE_KEY_CLICK);

        return super.onSingleTapUp(e);
    }

    @Override
    public void onLongPress(MotionEvent e) {
        TouchpadButton bt = (TouchpadButton) view;
        Log.d(TAG,"LONG CLICK");
        if (bt.isButtonPressed()) {
            bt.toggle();
            bt.buttonPress();
            sendCommand(view,Actions.MOUSE_UP_ACTION);
        } else
        {
            sendCommand(view,Actions.MOUSE_DOWN_ACTION);
            bt.toggle();
            bt.buttonPress();
        }
    }

    /**
     * @see Actions
     * @param v
     * @param actionCode - Actions mouse action codes
     */
    private void sendCommand(View v,byte actionCode) {
        int keyCode = Integer.parseInt(v.getTag().toString());
        byte[] bytes = new byte[]{Actions.MOUSE_ACTION, actionCode, (byte) keyCode};
        connector.sendUdp(bytes);
    }

}