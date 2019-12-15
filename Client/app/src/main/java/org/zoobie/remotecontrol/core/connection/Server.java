package org.zoobie.remotecontrol.core.connection;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Server {
    private InetAddress ipAdress;

    private Integer udpPort;
    private Integer tcpPort;

    public Server(String ipAdress, Integer udpPort, Integer tcpPort) {
        try {
            this.ipAdress = InetAddress.getByName(ipAdress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.udpPort = udpPort;
        this.tcpPort = tcpPort;
    }

    public Integer getUdpPort() {
        return udpPort;
    }

    public boolean isValid(){
        return ipAdress!=null && udpPort!=null && tcpPort!=null;
    }
    public void setUdpPort(int udpPort) {
        this.udpPort = udpPort;
    }

    public Integer getTcpPort() {
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
