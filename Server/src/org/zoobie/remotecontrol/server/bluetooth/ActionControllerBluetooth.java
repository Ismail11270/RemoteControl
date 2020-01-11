package org.zoobie.remotecontrol.server.bluetooth;

import org.zoobie.remotecontrol.core.actions.ActionExecutor;
import org.zoobie.remotecontrol.server.Server;


public class ActionControllerBluetooth {

    private final ActionExecutor actionExecutor;

    ActionControllerBluetooth(Server server) {
        actionExecutor = new ActionExecutor(server);
    }

    void performAction(byte[] actionCode) {
        actionExecutor.execute(actionCode);
    }

}
