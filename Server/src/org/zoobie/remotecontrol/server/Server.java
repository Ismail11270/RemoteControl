package org.zoobie.remotecontrol.server;

import org.zoobie.remotecontrol.core.actions.ActionController;

import java.io.IOException;
import java.net.*;

public class Server {

    private DatagramSocket recieveSocket;
    private DatagramSocket sendSocket;
    private DatagramPacket packet;
    private int port = 1711;
    private byte[] byteData;
    private InetAddress ip;
    private ActionController actionController;
    public Server() {
        try {
            recieveSocket = new DatagramSocket(port);
            ip = InetAddress.getByName("localhost");
            actionController = new ActionController(this);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        new Thread(() -> {
            try {
                while (true) {
                    byteData = new byte[3];
                    packet = new DatagramPacket(byteData,byteData.length);
                    recieveSocket.receive(packet);
                    System.out.println("recieved");
                    synchronized (actionController) {
                        actionController.performAction(packet);
                    }
                }
            } catch (IOException | InterruptedException e) {
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

}
