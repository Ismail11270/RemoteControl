package org.zoobie.remotecontrol.server.bluetooth;

import javax.bluetooth.RemoteDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BluetoothServerListener implements Runnable{
    private final int RECEIVER_BYTE_SIZE = 10;
    private ServerBluetooth serverBluetooth;
    private StreamConnectionNotifier scn;
    BluetoothServerListener(ServerBluetooth serverBluetooth) throws IOException {
        this.serverBluetooth = serverBluetooth;
        scn = (StreamConnectionNotifier) Connector.open(serverBluetooth.getAddress());
    }

    @Override
    public void run() {
        try{
            StreamConnection connection = scn.acceptAndOpen();
            RemoteDevice client = RemoteDevice.getRemoteDevice(connection);
            System.out.println("Connected to " + client.getBluetoothAddress());
            InputStream receiverStream =  connection.openInputStream();
            OutputStream replyStream = connection.openOutputStream();

            byte[] receivedData = receiverStream.readNBytes(RECEIVER_BYTE_SIZE);
            serverBluetooth.getActionController().performAction(receivedData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
