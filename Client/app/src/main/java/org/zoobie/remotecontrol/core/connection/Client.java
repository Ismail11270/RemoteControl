package org.zoobie.remotecontrol.core.connection;

public interface Client {
    boolean checkConnection() throws Exception;

    void send(byte... data);
}
