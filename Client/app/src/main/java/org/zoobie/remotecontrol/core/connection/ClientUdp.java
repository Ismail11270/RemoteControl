package org.zoobie.remotecontrol.core.connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ClientUdp {
    Server server;
    DatagramSocket socket;

    public ClientUdp(Server server) {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.server = server;
    }

    public void sendData(final CharSequence d) {
        final String data = d.toString();
        new Thread(() -> {
            try {
                byte[] byteData;
                byteData = data.getBytes();
                DatagramPacket packet = new DatagramPacket(byteData, byteData.length, server.getIpAdress(), server.getUdpPort());
                socket.send(packet);
                System.out.println(data);
            } catch (SocketException ex) {
//                    ex.printStackTrace();
            } catch (IOException e) {
//                    e.printStackTrace();
            }
        }).start();
    }
}
