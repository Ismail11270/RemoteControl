package org.zoobie.remotecontrol.tabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.zoobie.pomd.remotecontrol.R;
import org.zoobie.remotecontrol.activity.ConnectionActivity;
import org.zoobie.remotecontrol.core.actions.Actions;
import org.zoobie.remotecontrol.core.connection.ConnectionException;
import org.zoobie.remotecontrol.core.connection.Connector;
import org.zoobie.remotecontrol.core.connection.udp.Server;

import java.util.concurrent.ExecutionException;

public class TextInputFragment extends androidx.fragment.app.Fragment {

    private final String TAG = "TextInputFragment";
    private EditText textInput;
    private ImageButton leftBt, rightBt;
    private SharedPreferences connectionSp;
    private Connector connector;
    private Context ctx;

    public TextInputFragment(Connector connector) {
        this.connector = connector;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        ctx = container.getContext();
        View view = inflater.inflate(R.layout.fragment_text_input, container, false);
        connectionSp = ctx.getSharedPreferences("org.zoobie.connectiondata", Context.MODE_PRIVATE);
        textInput = view.findViewById(R.id.textInput);
        leftBt = view.findViewById(R.id.arrowLeft);
        rightBt = view.findViewById(R.id.arrowRight);


        leftBt.setOnClickListener(v -> {
            connector.send(Actions.KEYBOARD_ACTION, Actions.SPECIAL_KEY_ACTION, Actions.Keys.ARROW_LEFT);
        });
        rightBt.setOnClickListener(v -> {
            connector.send(Actions.KEYBOARD_ACTION, Actions.SPECIAL_KEY_ACTION, Actions.Keys.ARROW_RIGHT);
        });

        textInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i(TAG, keyCode + " key pressed");
                if(event.getAction() == KeyEvent.ACTION_DOWN)
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DEL:
                        connector.send(Actions.KEYBOARD_ACTION, Actions.SPECIAL_KEY_ACTION, Actions.Keys.BACKSPACE);
                        break;
                    case KeyEvent.KEYCODE_ENTER:
                        connector.send(Actions.KEYBOARD_ACTION, Actions.SPECIAL_KEY_ACTION, Actions.Keys.ENTER);
                        break;
                }
                return true;
            }
        });

        textInput.addTextChangedListener(new TextWatcher() {
            boolean isChanged = true;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i(TAG,s.toString());
                if (before < count && isChanged)
                    connector.send(Actions.KEYBOARD_ACTION, Actions.TEXT_KEY_ACTION, (byte) s.charAt(before));
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals(""))
                    textInput.setText("");
            }
        });
        return view;
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
            Toast.makeText(ctx, "Connected to " + connector.getServerName(), Toast.LENGTH_SHORT).show();
        } catch (ConnectionException | ExecutionException | InterruptedException e) {
            Toast.makeText(ctx, "FAILED TO CONNECT", Toast.LENGTH_SHORT).show();
            Intent connectionIntent = new Intent(ctx, ConnectionActivity.class);
            startActivity(connectionIntent);
        }
    }
}
