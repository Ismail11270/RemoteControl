package org.zoobie.remotecontrol.core.connection;

import org.zoobie.remotecontrol.core.controller.ConnectionController;

public class ConnectionException extends Exception {
    public ConnectionException(String msg){
        super(msg);
    }

    public ConnectionException(){
        this("Error establishing connection!");
    }
}
