package org.zoobie.remotecontrol.server.udp;

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
    private ActionControllerUdp actionControllerUdp;

    public ServerUdp(int port) {
        try {
            this.port = port;
            recieveSocket = new DatagramSocket(port);
            ip = InetAddress.getLocalHost();
            actionControllerUdp = new ActionControllerUdp(this);
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
                    synchronized (actionControllerUdp) {
                        actionControllerUdp.performAction(packet);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void reply(DatagramPacket packet, byte[] replyData) throws IOException {
        System.out.println(packet.getAddress() + ":" + packet.getPort());
        packet.setData(replyData);
        recieveSocket.send(packet);
    }

    public void send(byte[] data) throws IOException {
        DatagramPacket dgPacket = new DatagramPacket(data, data.length, ip, port);
        System.out.println("sent");
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
