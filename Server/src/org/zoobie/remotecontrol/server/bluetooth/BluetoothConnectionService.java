package org.zoobie.remotecontrol.server.bluetooth;

import javax.bluetooth.RemoteDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class BluetoothConnectionService implements Runnable{
    private final int RECEIVER_BYTE_SIZE = 10;
    private ServerBluetooth serverBluetooth;
    private StreamConnectionNotifier scn;
    private StreamConnection connection;
    BluetoothConnectionService(ServerBluetooth serverBluetooth) throws IOException {
        this.serverBluetooth = serverBluetooth;
        scn = (StreamConnectionNotifier) Connector.open(serverBluetooth.getAddress());
    }

    @Override
    public void run() {
        while(true) {
            try {
                connection = scn.acceptAndOpen();
                RemoteDevice client = RemoteDevice.getRemoteDevice(connection);
                System.out.println("Connected to " + client.getBluetoothAddress());
                InputStream receiverStream = connection.openInputStream();
                System.out.println("OPENED");
                byte[] receivedData = receiverStream.readNBytes(RECEIVER_BYTE_SIZE);
                System.out.println("BLUETOOTH RECIEVED " + Arrays.toString(receivedData));
                serverBluetooth.getActionController().performAction(receivedData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void reply(byte... data) throws IOException {
        OutputStream replyStream = connection.openOutputStream();
        replyStream.write(data);
    }
}
