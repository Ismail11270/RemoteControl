package org.zoobie.remotecontrol.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

public class TrackPadView extends View {
    public TrackPadView(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        super.onTouchEvent(event);
        return true;
    }
}
