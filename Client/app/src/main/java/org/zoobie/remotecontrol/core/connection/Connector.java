package org.zoobie.remotecontrol.core.connection;


import org.zoobie.remotecontrol.core.connection.bluetooth.BluetoothClient;
import org.zoobie.remotecontrol.core.connection.udp.Server;
import org.zoobie.remotecontrol.core.connection.udp.UdpClient;

import java.util.concurrent.ExecutionException;

/**
 * Connector class storing all possible connections
 * Performs sending actions
 */
public class Connector {
    private Server server;
    private UdpClient udpClient;

    public Connector(Server server) throws ConnectionException {
        if(!server.isValid()) throw new ConnectionException("Server is not valid");
        this.server = server;
        udpClient = new UdpClient(server);
    }

    public int checkConnection() throws ExecutionException, InterruptedException {
        int connections = 0;
        if(checkUdpConnection()) connections++;
        return connections;
    }

    public boolean checkUdpConnection() throws ExecutionException, InterruptedException {
        return udpClient.checkConnection();
    }

    public void sendUdp(byte[] data){
        if(udpClient == null)
            udpClient = new UdpClient(server);
        udpClient.send(data);
    }

    public String requestServerName() throws ExecutionException, InterruptedException {
        return udpClient.askServerName();
    }


    public void send(byte... data){
        sendUdp(data);
    }

}

