package org.zoobie.remotecontrol.core.connection;



public class ConnectionException extends Exception {
    public ConnectionException(String msg){
        super(msg);
    }

    public ConnectionException(){
        this("Error establishing connection!");
    }
}
