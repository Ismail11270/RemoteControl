package org.zoobie.remotecontrol.server;

import org.zoobie.remotecontrol.core.actions.ActionController;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

public class Server {

    private DatagramSocket socket;
    private DatagramPacket packet;
    private int port = 1711;
    private byte[] byteData;
    private InetAddress ip;
    private ActionController actionController;
    public Server() {
        try {
            socket = new DatagramSocket(port);
            ip = InetAddress.getByName("localhost");
            actionController = new ActionController();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        new Thread(() -> {
            try {
                while (true) {
                    byteData = new byte[1024];
                    packet = new DatagramPacket(byteData,byteData.length);
                    socket.receive(packet);
                    System.out.println("recieved");

                    byte[] byteData = packet.getData();
                    synchronized (actionController) {
                        actionController.performAction(byteData);
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public void send(String text) throws IOException {
            byte[] bytes = text.getBytes();
            DatagramSocket dgSocket = new DatagramSocket();
            DatagramPacket dgPacket = new DatagramPacket(bytes, bytes.length, ip, port);
            dgSocket.send(dgPacket);

    }

}
