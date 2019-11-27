package org.zoobie.pomd.remotecontrol.fragment.tabs;

import android.content.Context;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.zoobie.pomd.remotecontrol.R;
import org.zoobie.pomd.remotecontrol.core.controller.TouchPadController;
import org.zoobie.pomd.remotecontrol.core.listener.TouchPadKeysListener;
import org.zoobie.pomd.remotecontrol.core.listener.TouchPadListener;

public class FirstTabFragment extends androidx.fragment.app.Fragment {

    private View trackPadView;
    private ImageButton leftClick, midClick, rightClick;
    private TouchPadListener touchPadListener;
    private GestureDetector gestureDetector;
    private TouchPadKeysListener touchPadKeysListener;
    private Context ctx;
    private TouchPadController touchPadController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        ctx = container.getContext();
        View view = inflater.inflate(R.layout.fragment_tab_one, container, false);

        initViews(view);

        //Setup code here
        touchPadListener = new TouchPadListener(ctx);
        gestureDetector = new GestureDetector(ctx,touchPadListener);
        touchPadController = new TouchPadController();
        touchPadKeysListener = new TouchPadKeysListener(touchPadController);
        trackPadView.setOnTouchListener(touchPadListener);

        leftClick.setOnClickListener(touchPadKeysListener);
        midClick.setOnClickListener(touchPadKeysListener);
        rightClick.setOnClickListener(touchPadKeysListener);

        return view;
    }

    private void initViews(View view) {
        trackPadView = view.findViewById(R.id.trackPad);
        leftClick = view.findViewById(R.id.leftClick);
        midClick = view.findViewById(R.id.midClick);
        rightClick = view.findViewById(R.id.rightClick);



//        leftClick.setLayoutParams(params);
//        midClick.setLayoutParams(params);
//        rightClick.setLayoutParams(params);
    }
}
