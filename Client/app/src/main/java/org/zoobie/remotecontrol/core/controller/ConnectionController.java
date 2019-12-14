package org.zoobie.remotecontrol.core.controller;

import org.zoobie.remotecontrol.core.connection.UdpClient;
import org.zoobie.remotecontrol.core.connection.ConnectionException;
import org.zoobie.remotecontrol.core.connection.Server;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class ConnectionController {
    private InetAddress serverIp;
    private Integer portUdp;
    private Integer portTcp;
    private UdpClient clientUdp;
    private Server server;

    public ConnectionController(String ip, Integer portUdp, Integer portTcp) {
        this.portTcp = portTcp;
        this.portUdp = portUdp;
        this.server = new Server(ip,portUdp,portTcp);
        this.serverIp = server.getIpAdress();
    }

    public ConnectionController(Server server){
        this.server = server;
        this.portTcp = server.getTcpPort();
        this.portUdp = server.getUdpPort();
        this.serverIp = server.getIpAdress();
    }

    public boolean checkConnection() throws ConnectionException, ExecutionException, InterruptedException {
        sendUdp(new byte[] {127,1});
        System.out.println("checked connection");
        byte[] recieved = clientUdp.receive();
        System.out.println(Arrays.toString(recieved));
        return recieved[0] == 1;
    }

    public void sendUdp(byte[] data){
        if(clientUdp == null)
            clientUdp = new UdpClient(server);
        clientUdp.sendData(data);
    }
}
