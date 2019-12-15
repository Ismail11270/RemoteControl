package org.zoobie.remotecontrol.core.actions;

import org.zoobie.remotecontrol.server.ServerUdp;

import java.io.IOException;
import java.net.DatagramPacket;

public class ConnectionAction implements Action {
    private final byte[] command;
    private final ServerUdp serverUdp;
    private final DatagramPacket senderPacket;
    public ConnectionAction(ServerUdp serverUdp, DatagramPacket senderPacket, byte... command){
        this.senderPacket = senderPacket;
        this.serverUdp = serverUdp;
        this.command = command;
    }


    @Override
    public void performAction() {
        switch(command[1]){
            case Actions.CONNECTION_CHECK_ACTION:
                reply((byte)1);
                break;
            case Actions.CONNECTION_GET_SERVER_NAME:
                reply(serverUdp.getMachineName().getBytes());
                break;
            default:
                break;
        }
    }

    private void reply(byte... reply) {
        try {
            System.out.println("Sending confirmation");
            serverUdp.reply(senderPacket, reply);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
