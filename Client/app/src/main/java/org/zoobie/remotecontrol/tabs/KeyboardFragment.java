package org.zoobie.remotecontrol.tabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.zoobie.pomd.remotecontrol.R;
import org.zoobie.remotecontrol.activity.ConnectionActivity;
import org.zoobie.remotecontrol.core.actions.Actions;
import org.zoobie.remotecontrol.core.connection.ConnectionException;
import org.zoobie.remotecontrol.core.connection.Connector;
import org.zoobie.remotecontrol.core.connection.udp.Server;
import org.zoobie.remotecontrol.view.KeyboardButton;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class KeyboardFragment extends androidx.fragment.app.Fragment implements View.OnClickListener, View.OnLongClickListener {


    private final String TAG = "KeyboardFragment";
    private SharedPreferences connectionSp;
    private Connector connector;
    private Context ctx;

    private ConstraintLayout keysLayout;
    private ArrayList<KeyboardButton> buttonsList;
    private Actions.Keys keys;
    private Resources res;
    private View view;

    public KeyboardFragment() {

    }

    public KeyboardFragment(Connector connector) {
        this.connector = connector;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        ctx = container.getContext();
        view = inflater.inflate(R.layout.fragment_keyboard, container, false);
        connectionSp = ctx.getSharedPreferences("org.zoobie.connectiondata", Context.MODE_PRIVATE);
        res = getResources();
        keys = new Actions.Keys(res);
        initViews();
        keyboardInit();
        return view;
    }

    private void keyboardInit() {

    }


    private void initViews() {
        keysLayout = (ConstraintLayout) view;
        ArrayList<View> views = keysLayout.getTouchables(); //get touchable children
        buttonsList = new ArrayList<>();
        for (View v : views) {
            if (v instanceof KeyboardButton) {
                KeyboardButton b = (KeyboardButton) v;
                b.setOnClickListener(this);
                b.setOnLongClickListener(this);
                buttonsList.add(b);
            }
        }
        System.out.println(buttonsList.size() + " VIEWS");
    }


    @Override
    public void onResume() {
        super.onResume();
        verifyConnection();
    }

    private void verifyConnection() {
        String ip = connectionSp.getString("server_ip", null);
        Integer portUdp = connectionSp.getInt("udp_port", -1) == -1 ? null : connectionSp.getInt("udp_port", -1);
        Server server = new Server(ip, portUdp);
        try {
            connector = new Connector(server);
            boolean isConnected = connector.checkConnection() > 0;
            if (!isConnected) throw new ConnectionException("Couldn't connect to the server");
            Toast.makeText(ctx, "Connected to " + connector.requestServerName(), Toast.LENGTH_SHORT).show();
        } catch (ConnectionException | ExecutionException | InterruptedException e) {
            Toast.makeText(ctx, "FAILED TO CONNECT", Toast.LENGTH_SHORT).show();
            Intent connectionIntent = new Intent(ctx, ConnectionActivity.class);
            startActivity(connectionIntent);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getTag() == res.getString(R.string.tag_play_pause)){
            connector.send(Actions.MEDIA_ACTION,Actions.MEDIA_PLAY_PAUSE);
        }
        else if (v instanceof KeyboardButton) {
            KeyboardButton b = (KeyboardButton) v;
            byte keyCode = keys.getActionCodeForKey(b.getTag().toString());
            if (b.isButtonPressed()) {
                b.toggle();
                b.buttonPress();
                connector.send(Actions.KEYBOARD_ACTION, Actions.SPECIAL_KEY_ACTION_RELEASE, keyCode);
            } else
                connector.send(Actions.KEYBOARD_ACTION, Actions.SPECIAL_KEY_ACTION_CLICK, keyCode);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v instanceof KeyboardButton) {
            KeyboardButton b = (KeyboardButton) v;
            if (!b.isToggleable()) onClick(v);
            else {
                byte keyCode = keys.getActionCodeForKey(b.getTag().toString());
                if (!b.isButtonPressed()) {
                    b.toggle();
                    b.buttonPress();
                    connector.send(Actions.KEYBOARD_ACTION, Actions.SPECIAL_KEY_ACTION_PRESS, keyCode);
                } else {
                    connector.send(Actions.KEYBOARD_ACTION, Actions.SPECIAL_KEY_ACTION_RELEASE, keyCode);
                    b.toggle();
                    b.buttonPress();
                }
            }
        }
        return true;
    }
}
