package org.zoobie.remotecontrol.core.connection.bluetooth;

import org.zoobie.remotecontrol.core.connection.Client;
@Deprecated
public class BluetoothClient implements Client {

    @Override
    public boolean checkConnection() {
        return false;
    }

    @Override
    public void send(byte... data) {

    }
}
