package org.zoobie.remotecontrol.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.zoobie.pomd.remotecontrol.R;
import org.zoobie.remotecontrol.core.connection.ConnectionException;
import org.zoobie.remotecontrol.core.connection.Connector;
import org.zoobie.remotecontrol.core.connection.Server;

import java.util.concurrent.ExecutionException;

public class ConnectionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button connectButton;
    private TextView connectionStatusLocalTv;
    private EditText ipAddressEt, udpPortEt, tcpPortEt;
    private SharedPreferences connectionSp;
    private static final int CONNECTION_RESULT = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        connectionSp = this.getSharedPreferences("org.zoobie.connectiondata", Context.MODE_PRIVATE);

        connectButton = findViewById(R.id.connectBt);
        connectionStatusLocalTv = findViewById(R.id.localConnectionStatus);
        ipAddressEt = findViewById(R.id.connectionIp);
        udpPortEt = findViewById(R.id.udpEt);
        tcpPortEt = findViewById(R.id.tcpEt);
        ipAddressEt.setText(connectionSp.getString("server_ip",""));
        udpPortEt.setText(connectionSp.getInt("udp_port",8888) + "");
        tcpPortEt.setText(connectionSp.getInt("tcp_port",9999) + "");
        connectButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.connectBt) {
            try {
                int tcpPort = Integer.parseInt(tcpPortEt.getText().toString());
                int udpPort = Integer.parseInt(udpPortEt.getText().toString());
                String ip = ipAddressEt.getText().toString();
                Server server = new Server(ip, udpPort, tcpPort);
                Connector connector = new Connector(server);
                boolean connected = connector.checkUdpConnection() & connector.checkTcpConnection();
                if(!connected) throw new ConnectionException("Not connected");
                Toast.makeText(this, "connection established successfully", Toast.LENGTH_LONG).show();
                connectionSp.edit().putInt("udp_port",udpPort).putInt("tcp_port",tcpPort).putString("server_ip",ip).apply();
                finish();
            } catch (NumberFormatException | ConnectionException | ExecutionException | InterruptedException e) {
                Toast.makeText(this, "Connection failed, please check the values.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
