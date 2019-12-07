package org.zoobie.remotecontrol.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class CustomViewPager extends ViewPager {
    private boolean isSwipable = false;

    public CustomViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs){
        super(context,attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isSwipable && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isSwipable && super.onTouchEvent(ev);
    }

    public void setSwipable(boolean isSwipable){
        this.isSwipable = isSwipable;
    }
}
