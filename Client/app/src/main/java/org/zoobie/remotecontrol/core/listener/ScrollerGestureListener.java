package org.zoobie.remotecontrol.core.listener;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import org.zoobie.remotecontrol.core.actions.Actions;
import org.zoobie.remotecontrol.core.connection.Connector;

public class ScrollerGestureListener extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {

    private Context context;
    private GestureDetector gestureDetector;
    private Connector connector;
    private final String TAG = "ScrollerListener";

    public ScrollerGestureListener(Context context, Connector connector) {
        this.connector = connector;
        this.context = context;
        gestureDetector = new GestureDetector(context, this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        int x = (int) distanceY;
        connector.send(Actions.MOUSE_ACTION,Actions.MOUSE_SCROLL_ACTION, (byte) x);
        Log.i(TAG, "Mouse wheel " + x);
        return true;
    }
}
