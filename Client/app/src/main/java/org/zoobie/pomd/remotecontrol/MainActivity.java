package org.zoobie.pomd.remotecontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;

import com.google.android.material.tabs.TabLayout;

import org.zoobie.pomd.remotecontrol.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private View view;

    private GestureDetector mGestureDetector;


    private Toolbar toolbar;

    //Views
    private ViewPager pager;
    private TabLayout tabLayout;

    //Adapters
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBar);
        toolbar.setNavigationIcon(R.drawable.navigation_icon);
        setSupportActionBar(toolbar);

        setup();

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),ViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(pager);
//        view = findViewById(R.id.view);
//        view.setOnTouchListener(this);
//
//        mGestureDetector = new GestureDetector(this,this);
    }

    private void setup() {
        //Toolbar setup
        toolbar = findViewById(R.id.toolBar);
        toolbar.setNavigationIcon(R.drawable.navigation_icon);
        setSupportActionBar(toolbar);

        //Views
        pager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tabs);
    }


//
//
//
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        mGestureDetector.onTouchEvent(event);
//        int action = event.getAction();
//
//        switch(action) {
//            case (MotionEvent.ACTION_DOWN) :
//                Log.d(TAG,"Action was DOWN");
//                return true;
//            case (MotionEvent.ACTION_MOVE) :
//                Log.d(TAG,"Action was MOVE " + event.getX() + "," + event.getY());
//                return true;
//            case (MotionEvent.ACTION_UP) :
//                Log.d(TAG,"Action was UP");
//                return true;
//            case (MotionEvent.ACTION_CANCEL) :
//                Log.d(TAG,"Action was CANCEL");
//                return true;
//            case (MotionEvent.ACTION_OUTSIDE) :
//                Log.d(TAG,"Movement occurred outside bounds " +
//                        "of current screen element");
//                return true;
//            default :
//                return super.onTouchEvent(event);
//        }
//    }
//
//    @Override
//    public boolean onDown(MotionEvent e) {
//        Log.d(TAG, "onDown");
//        return true;
//    }
//
//    @Override
//    public void onShowPress(MotionEvent e) {
//        Log.d(TAG, "onShowPress");
//    }
//
//    @Override
//    public boolean onSingleTapUp(MotionEvent e) {
//        Log.d(TAG, "onSignleTapUp");
//        return true;
//    }
//
//    @Override
//    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//        //Log.d(TAG, "onScroll");
//        return true;
//    }
//
//    @Override
//    public void onLongPress(MotionEvent e) {
//        Log.d(TAG, "onLongPress");
//    }
//
//    @Override
//    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        Log.d(TAG, "onFling " + velocityX + "," + velocityY);
//        return true;
//    }
}
