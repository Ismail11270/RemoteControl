package org.zoobie.remotecontrol.server;

import javax.bluetooth.RemoteDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connection;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ServerBluetooth {
    private static final String SERVER_NAME = "Bluetooth Server";
    private UUID uuid;
    private final String address;
    private RemoteDevice remoteDevice;
    private StreamConnection connection;
    private InputStream recieverStream;
    private OutputStream senderStream;

    public ServerBluetooth() throws IOException {
        uuid = new UUID("00230103210002A003B21F32F32", false);
        address = "btspp://localhost:" + uuid + ";name=" + SERVER_NAME;
        StreamConnectionNotifier scn = (StreamConnectionNotifier) Connector.open(address);
        System.out.println("Server running, waiting for connection...");
        connection = scn.acceptAndOpen();
        remoteDevice = RemoteDevice.getRemoteDevice(connection);
        System.out.println("Connected to " + remoteDevice.getBluetoothAddress());

    }
    
    public void listen() throws IOException {
        recieverStream =  connection.openInputStream();
        senderStream = connection.openOutputStream();
        System.out.println(1);
        String lineRead = new String(recieverStream.readNBytes(20))+ " ";
        System.out.println(2);
        System.out.println(lineRead);
        senderStream.write("hello\n".getBytes());
        senderStream.flush();
        recieverStream.close();
        senderStream.close();
    }
}
