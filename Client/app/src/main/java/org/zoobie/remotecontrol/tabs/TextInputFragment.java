package org.zoobie.remotecontrol.tabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.zoobie.pomd.remotecontrol.R;
import org.zoobie.remotecontrol.activity.ConnectionActivity;
import org.zoobie.remotecontrol.core.connection.ConnectionException;
import org.zoobie.remotecontrol.core.connection.Connector;
import org.zoobie.remotecontrol.core.connection.Server;

import java.util.concurrent.ExecutionException;

public class TextInputFragment extends androidx.fragment.app.Fragment {

    private EditText textInput;
    private ImageButton leftBt, rightBt;
    private Button backspaceBt;
    private SharedPreferences connectionSp;
    private Connector connector;
    private Context ctx;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        ctx = container.getContext();
        View view = inflater.inflate(R.layout.fragment_tab_two, container, false);

        textInput = view.findViewById(R.id.textInput);
        leftBt = view.findViewById(R.id.arrowLeft);
        rightBt = view.findViewById(R.id.arrowRight);
        backspaceBt = view.findViewById(R.id.backspace);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initConnection();
    }

    private void initConnection() {
        String ip = connectionSp.getString("server_ip", null);
        Integer portUdp = connectionSp.getInt("udp_port", -1) == -1 ? null : connectionSp.getInt("udp_port", -1);
        Integer portTcp = connectionSp.getInt("tcp_port", -1) == -1 ? null : connectionSp.getInt("tcp_port", -1);
        Server server = new Server(ip, portUdp, portTcp);
        try {
            connector = new Connector(server);
            boolean isConnected = connector.checkTcpConnection() | connector.checkBluetoothConnection();
            if (!isConnected) throw new ConnectionException("Couldn't connect to the server");
            Toast.makeText(ctx, "Connected to " + connector.getServerName(), Toast.LENGTH_SHORT).show();
        } catch (ConnectionException | ExecutionException | InterruptedException e) {
            Toast.makeText(ctx, "FAILED TO CONNECT", Toast.LENGTH_SHORT).show();
            Intent connectionIntent = new Intent(ctx, ConnectionActivity.class);
            startActivity(connectionIntent);
        }
    }
}
