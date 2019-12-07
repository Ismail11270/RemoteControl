package org.zoobie.remotecontrol.core.connection;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Server {
    private InetAddress ipAdress;

    private int udpPort;
    private int tcpPort;

    public Server(String ipAdress, int udpPort, int tcpPort) {
        try {
            this.ipAdress = InetAddress.getByName(ipAdress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.udpPort = udpPort;
        this.tcpPort = tcpPort;
    }

    public int getUdpPort() {
        return udpPort;
    }

    public void setUdpPort(int udpPort) {
        this.udpPort = udpPort;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }

    public InetAddress getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(InetAddress ipAdress) {
        this.ipAdress = ipAdress;
    }
}
