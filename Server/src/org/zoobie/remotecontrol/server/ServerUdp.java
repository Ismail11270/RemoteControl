package org.zoobie.remotecontrol.server;

import org.zoobie.remotecontrol.core.actions.ActionController;

import java.io.IOException;
import java.net.*;

public class ServerUdp {

    private static final int MAX_PACKET_SIZE = 5;
    private DatagramSocket recieveSocket;
    private DatagramSocket sendSocket;
    private DatagramPacket packet;
    private int port;
    private byte[] byteData;
    private InetAddress ip;
    private ActionController actionController;

    public ServerUdp(int port) {
        try {
            this.port = port;
            recieveSocket = new DatagramSocket(port);
            ip = InetAddress.getLocalHost();
            actionController = new ActionController(this);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        new Thread(() -> {
            try {
                while (true) {
                    byteData = new byte[MAX_PACKET_SIZE];
                    packet = new DatagramPacket(byteData, byteData.length);
                    recieveSocket.receive(packet);
                    synchronized (actionController) {
                        actionController.performAction(packet);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error receiving packet!");
            }
        }).start();
    }

    public void reply(DatagramPacket packet, byte[] replyData) throws IOException {
        packet.setData(replyData);
        recieveSocket.send(packet);
    }

    public void send(byte[] data) throws IOException {
        DatagramPacket dgPacket = new DatagramPacket(data, data.length, ip, port);
        recieveSocket.send(dgPacket);
    }

    public String getMachineName() {
        return ip.getHostName();
    }

    public String getIp(){
        return ip.getHostAddress();
    }

    public int getPort() {
        return port;
    }
}
