package org.zoobie.remotecontrol.tabs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.inputmethodservice.Keyboard;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import org.zoobie.pomd.remotecontrol.R;
import org.zoobie.remotecontrol.core.listener.ScrollerGestureListener;
import org.zoobie.remotecontrol.core.listener.TouchPadKeysGestureListener;
import org.zoobie.remotecontrol.activity.ConnectionActivity;
import org.zoobie.remotecontrol.core.connection.ConnectionException;
import org.zoobie.remotecontrol.core.connection.Connector;
import org.zoobie.remotecontrol.core.connection.udp.Server;
import org.zoobie.remotecontrol.core.listener.TouchPadGestureListener;
import org.zoobie.remotecontrol.core.listener.TouchPadKeysListener;
import org.zoobie.remotecontrol.view.KeyboardButton;
import org.zoobie.remotecontrol.view.TouchpadButton;

import java.util.concurrent.ExecutionException;

public class TrackPadFragment extends androidx.fragment.app.Fragment {

    private static final int CONNECTION_RESULT = 123;
    private static final String TAG = "TRACKPAD";
    private View trackPadView, scrollerView;
    private TouchpadButton leftClick, midClick, rightClick;
    private TouchPadKeysListener touchPadKeysListener;
    private TouchPadKeysGestureListener touchPadKeysGestureListener;
    private TouchPadGestureListener touchPadGestureListener;
    private Context ctx;
    private Connector connector;
    private SharedPreferences connectionSp;
    private SharedPreferences settingsSp;
    private float sens;
    private ScrollerGestureListener scrollerGestureListener;
    private View view;
    private Guideline guideline;
    public TrackPadFragment(){

    }
    public TrackPadFragment(Connector connector){
        this.connector = connector;
    }
    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        ctx = container.getContext();
        view = inflater.inflate(R.layout.fragment_trackpad, container, false);
        connectionSp = ctx.getSharedPreferences("org.zoobie.connectiondata", Context.MODE_PRIVATE);
        settingsSp = ctx.getSharedPreferences("org.zoobie.settings", Context.MODE_PRIVATE);
        initViews();
        Configuration configuration = new Configuration();
        configuration.orientation = getActivity().getResources().getConfiguration().orientation;
        onConfigurationChanged(configuration);


        //Setup code here
//        verifyConnection();

        touchPadKeysListener = new TouchPadKeysListener(connector);
        touchPadGestureListener = new TouchPadGestureListener(ctx, connector);
        touchPadKeysGestureListener = new TouchPadKeysGestureListener(ctx,connector,touchPadGestureListener);
        scrollerGestureListener = new ScrollerGestureListener(ctx,connector);
        trackPadView.setOnTouchListener(touchPadGestureListener);
        scrollerView.setOnTouchListener(scrollerGestureListener);
        leftClick.setOnTouchListener(touchPadKeysGestureListener);
        midClick.setOnTouchListener(touchPadKeysGestureListener);
        rightClick.setOnTouchListener(touchPadKeysGestureListener);
        updateSettings();
        return view;
    }


    private void verifyConnection() {
        String ip = connectionSp.getString("server_ip", null);
        Integer portUdp = connectionSp.getInt("udp_port", -1) == -1 ? null : connectionSp.getInt("udp_port", -1);
        Server server = new Server(ip, portUdp);
        try {
            connector = new Connector(server);
            boolean isConnected = connector.checkUdpConnection() | connector.checkBluetoothConnection();
            if (!isConnected) throw new ConnectionException("Couldn't connect to the server");
            Toast.makeText(ctx, "Connected to " + connector.getServerName(), Toast.LENGTH_SHORT).show();
        } catch (ConnectionException | ExecutionException | InterruptedException e) {
            Toast.makeText(ctx, "FAILED TO CONNECT", Toast.LENGTH_SHORT).show();
            Intent connectionIntent = new Intent(ctx, ConnectionActivity.class);
            startActivityForResult(connectionIntent, CONNECTION_RESULT);
        }
    }

    private void updateSettings() {
        sens = connectionSp.getFloat("sens", 1.0f);
        touchPadGestureListener.setSens(sens);
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG,"ORIENTATION CHANGED");
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            guideline.setGuidelinePercent(0.70f);
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        } else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            TypedValue typedValue = new TypedValue();
            getResources().getValue(R.dimen.mouse_buttons_guideline,typedValue,true);
            float value = typedValue.getFloat();
            guideline.setGuidelinePercent(value);
            ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        }
    }


    @Override
    public void onResume() {
        verifyConnection();
        updateSettings();
        super.onResume();
        System.out.println("resume");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONNECTION_RESULT) {
            System.out.println(resultCode + " CONNECTION RESULT");
        }
    }

    private void initViews() {
        scrollerView = view.findViewById(R.id.scroller);
        trackPadView = view.findViewById(R.id.trackPad);
        leftClick = view.findViewById(R.id.leftClick);
        midClick = view.findViewById(R.id.midClick);
        rightClick = view.findViewById(R.id.rightClick);
        guideline = view.findViewById(R.id.guideline);

        midClick.setBackground(getResources().getDrawable(R.drawable.mouse_middle_button_background,null));
        midClick.setBackground(R.drawable.mouse_middle_button_background);
        midClick.setBackgroundPressed(R.drawable.mouse_buttons_background_pressed_middle);
    }
}
