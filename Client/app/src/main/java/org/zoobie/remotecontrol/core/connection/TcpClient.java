package org.zoobie.remotecontrol.core.connection;

public class TcpClient implements Client{
    public TcpClient(Server server) {

    }

    @Override
    public boolean checkConnection() {
        return true;
    }

    @Override
    public void send(byte... data) {

    }
}
