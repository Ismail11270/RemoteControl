package org.zoobie.remotecontrol.core.connection;

public class BluetoothClient implements Client{

    @Override
    public boolean checkConnection() {
        return false;
    }

    @Override
    public void send(byte... data) {

    }
}
