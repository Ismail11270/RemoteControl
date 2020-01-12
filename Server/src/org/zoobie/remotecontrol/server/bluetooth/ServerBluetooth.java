package org.zoobie.remotecontrol.server.bluetooth;

import org.zoobie.remotecontrol.server.Server;

import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import java.io.IOException;

public class ServerBluetooth implements Server {
    static final String SERVER_NAME = "Bluetooth Server";
    private UUID uuid;
    private final String address;
    private BluetoothServerListener connectionListener;
    private ActionControllerBluetooth actionController;
    private LocalDevice localDevice;
    public ServerBluetooth() throws IOException {
        uuid = new UUID("02fc312020003230100002f2afa2111b", false);
        address = "btspp://localhost:" + uuid + ";name=" + SERVER_NAME;
        System.out.println(SERVER_NAME + " started!");
        connectionListener = new BluetoothServerListener(this);
        actionController = new ActionControllerBluetooth(this);
        localDevice = LocalDevice.getLocalDevice();
        localDevice.setDiscoverable(DiscoveryAgent.GIAC);
    }
    public String getAddress(){
        return address;
    }
    public void startServer(){
        new Thread(connectionListener).start();
    }


    @Override
    public String getMachineName() {
        return null;
    }

    @Override
    public void reply(byte[] bytes) {

    }

    public ActionControllerBluetooth getActionController() {
        return actionController;
    }
}
