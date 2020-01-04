package org.zoobie.remotecontrol.core.connection.udp;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Server {
    private InetAddress ipAdress;

    private Integer port;

    public Server(String ipAdress, Integer port) {
        try {
            this.ipAdress = InetAddress.getByName(ipAdress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        this.port = port;
    }

    public Integer getPort() {
        return port;
    }

    public boolean isValid(){
        return ipAdress!=null && port !=null;
    }
    public void setPort(int port) {
        this.port = port;
    }



    public InetAddress getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(InetAddress ipAdress) {
        this.ipAdress = ipAdress;
    }
}
