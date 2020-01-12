package org.zoobie.remotecontrol.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.zoobie.pomd.remotecontrol.R;
import org.zoobie.remotecontrol.core.connection.ConnectionException;
import org.zoobie.remotecontrol.core.connection.Connector;
import org.zoobie.remotecontrol.core.connection.udp.Server;

import java.util.Set;
import java.util.concurrent.ExecutionException;

public class ConnectionActivity extends AppCompatActivity implements View.OnClickListener {

    private Button connectButton, refreshButton, scanButton;
    private TextView connectionStatusLocalTv;
    private EditText ipAddressEt, udpPortEt;
    private SharedPreferences connectionSp;
    private Toolbar toolbar;
    private LinearLayout bluetoothSettingsLayout;
    private BluetoothAdapter bluetoothAdapter;
    private TextView bluetoothMessage, bluetoothTitleTv;
    private ArrayAdapter<String> pairedDevicesArrayAdapter;
    private ListView pairedDevicesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        connectionSp = this.getSharedPreferences("org.zoobie.connectiondata", Context.MODE_PRIVATE);

        toolbar = findViewById(R.id.toolBar);
        toolbar.setNavigationIcon(R.drawable.navigation_icon);
        setSupportActionBar(toolbar);
        connectButton = findViewById(R.id.connectBt);
        connectionStatusLocalTv = findViewById(R.id.localConnectionStatus);
        ipAddressEt = findViewById(R.id.connectionIp);
        udpPortEt = findViewById(R.id.udpEt);
        bluetoothTitleTv = findViewById(R.id.bluetoothTitle);
        refreshButton = findViewById(R.id.refreshBt);
//        scanButton = findViewById(R.id.scanBtn);
        bluetoothMessage = findViewById(R.id.bluetoothMessage);
        pairedDevicesList = findViewById(R.id.devicesList);
        bluetoothSettingsLayout = findViewById(R.id.bluetoothSettings);
        ipAddressEt.setText(connectionSp.getString("server_ip", ""));
        udpPortEt.setText(connectionSp.getInt("udp_port", 8888) + "");
        connectButton.setOnClickListener(this);

        pairedDevicesArrayAdapter = new ArrayAdapter<>(this, R.layout.device_list_element);
        pairedDevicesList.setAdapter(pairedDevicesArrayAdapter);
        pairedDevicesList.setOnItemClickListener(mOnItemClickListener);

        verifyBluetooth();
        refreshButton.setOnClickListener(v -> verifyBluetooth());
//        scanButton.setOnClickListener(v -> verifyBluetooth());

    }

    @Override
    protected void onResume() {
        super.onResume();
        verifyBluetooth();
    }

    private void scanForDevices() {

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        pairedDevicesArrayAdapter.clear();
        // If there are paired devices, add each one to the ArrayAdapter
        System.out.println(pairedDevices.size() + " NUMBER OF PAIRED DEVICES");
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                pairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        } else {
            String noDevices = "None paired";
            pairedDevicesArrayAdapter.add(noDevices);
        }
    }

    private boolean verifyBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            bluetoothMessage.setText("Device doesn't support bluetooth :(");
            bluetoothMessage.setVisibility(View.VISIBLE);
            bluetoothSettingsLayout.setVisibility(View.GONE);
            refreshButton.setVisibility(View.VISIBLE);
            return false;
        } else if (bluetoothAdapter.isEnabled()) {
            bluetoothMessage.setVisibility(View.INVISIBLE);
            bluetoothSettingsLayout.setVisibility(View.VISIBLE);
            refreshButton.setVisibility(View.GONE);
            scanForDevices();
            return true;
        } else if (!bluetoothAdapter.isEnabled()) {
            bluetoothMessage.setText("Please enable bluetooth to be able to use bluetooth connection");
            bluetoothMessage.setVisibility(View.VISIBLE);
            bluetoothSettingsLayout.setVisibility(View.GONE);
            refreshButton.setVisibility(View.VISIBLE);
            return false;
        }
        return false;
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = (parent, view, position, id) -> {
        try {
            String split[] = ((TextView) view).getText().toString().split("\n");
            BluetoothDevice remoteDevice = bluetoothAdapter.getRemoteDevice(split[1]);
            Connector connector = new Connector(remoteDevice);
            boolean connected = connector.checkBluetoothConnection();
            if (!connected) throw new ConnectionException("Not connected");
            Toast.makeText(this, "connection established successfully", Toast.LENGTH_LONG).show();
            connectionSp.edit().putString("bluetooth_address", split[1]).putBoolean("bt_connection",true).apply();
            finish();
        }catch(ConnectionException e){
            Toast.makeText(this, "Connection failed, please check the server.", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.connectBt) {
            try {
                int udpPort = Integer.parseInt(udpPortEt.getText().toString());
                String ip = ipAddressEt.getText().toString();
                Server server = new Server(ip, udpPort);
                Connector connector = new Connector(server);
                boolean connected = connector.checkUdpConnection();
                if (!connected) throw new ConnectionException("Not connected");
                Toast.makeText(this, "connection established successfully", Toast.LENGTH_LONG).show();
                connectionSp.edit().putBoolean("bt_connection",false).putInt("udp_port", udpPort).putString("server_ip", ip).apply();
                finish();
            } catch (NumberFormatException | ConnectionException | ExecutionException | InterruptedException e) {
                Toast.makeText(this, "Connection failed, please check the server.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
