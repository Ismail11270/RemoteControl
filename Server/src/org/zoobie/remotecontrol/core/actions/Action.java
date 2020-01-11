package org.zoobie.remotecontrol.core.actions;

public interface Action {


    //later to be removed as the udp server will start using the actionexecutor
    void performActionUdp();

    void performAction();

}
