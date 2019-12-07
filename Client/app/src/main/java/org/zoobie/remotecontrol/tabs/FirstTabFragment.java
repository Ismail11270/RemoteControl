package org.zoobie.remotecontrol.tabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.zoobie.pomd.remotecontrol.R;
import org.zoobie.remotecontrol.core.connection.Server;
import org.zoobie.remotecontrol.core.listener.TouchPadKeysListener;
import org.zoobie.remotecontrol.core.listener.TouchPadListener;

public class FirstTabFragment extends androidx.fragment.app.Fragment {

    private View trackPadView;
    private ImageButton leftClick, midClick, rightClick;
    private TouchPadListener touchPadListener;
    private GestureDetector gestureDetector;
    private TouchPadKeysListener touchPadKeysListener;
    private Context ctx;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        ctx = container.getContext();
        View view = inflater.inflate(R.layout.fragment_tab_one, container, false);

        initViews(view);

        //Setup code here
        SharedPreferences sp = ctx.getSharedPreferences("org.zoobie.pomd.remotecontrol",Context.MODE_PRIVATE);
        String ip = sp.getString("server_ip",null);
        int portUdp =  sp.getInt("udp_port",-1);
        int portTcp =  sp.getInt("tcp_port",-1);
        Server server = new Server("157.158.170.23",1711,portTcp);
        touchPadListener = new TouchPadListener(ctx,server);
        gestureDetector = new GestureDetector(ctx,touchPadListener);
        touchPadKeysListener = new TouchPadKeysListener(server);
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
