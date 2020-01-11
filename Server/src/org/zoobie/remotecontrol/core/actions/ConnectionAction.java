package org.zoobie.remotecontrol.core.actions;

import org.zoobie.remotecontrol.server.Server;
import org.zoobie.remotecontrol.server.udp.ServerUdp;

import java.io.IOException;
import java.net.DatagramPacket;

public class ConnectionAction implements Action {
    private final byte[] command;
    private Server server;

    private ServerUdp serverUdp;
    private DatagramPacket senderPacket;

    public ConnectionAction(ServerUdp serverUdp, DatagramPacket senderPacket, byte... command) {
        this.senderPacket = senderPacket;
        this.serverUdp = serverUdp;
        this.command = command;
    }

    public ConnectionAction(Server server, byte... command) {
        this.server = server;
        this.command = command;
    }


    @Override
    public void performActionUdp() {
        switch (command[1]) {
            case Actions.CONNECTION_CHECK_ACTION:
                replyUdp((byte) 1);
                break;
            case Actions.CONNECTION_GET_SERVER_NAME:
                replyUdp(serverUdp.getMachineName().getBytes());
                break;
            default:
                break;
        }
    }

    @Override
    public void performAction() {
        switch (command[1]) {
            case Actions.CONNECTION_CHECK_ACTION:
                replyUdp((byte) 1);
                break;
            case Actions.CONNECTION_GET_SERVER_NAME:
                reply(server.getMachineName().getBytes());
                break;
            default:
                break;
        }
    }

    private void reply(byte[] bytes) {
        System.out.println("Sending reply to the client");
        server.reply(bytes);
    }

    private void replyUdp(byte... reply) {
        try {
            System.out.println("Sending confirmation");
            serverUdp.reply(senderPacket, reply);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
