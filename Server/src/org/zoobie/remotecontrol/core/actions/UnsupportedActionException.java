package org.zoobie.remotecontrol.core.actions;

public class UnsupportedActionException extends Exception {
    public UnsupportedActionException(String msg){
        super(msg);
    }

    public UnsupportedActionException(){
        this("Action is unsupported");
    }
}
