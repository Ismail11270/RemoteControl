package org.zoobie.remotecontrol.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class TrackPadView extends View {
    public TrackPadView(Context context) {
        this(context, null, -1, -1);
    }

    public TrackPadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public TrackPadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TrackPadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1, -1);
    }
}
