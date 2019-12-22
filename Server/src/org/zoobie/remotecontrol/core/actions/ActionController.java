package org.zoobie.remotecontrol.core.actions;

import org.zoobie.remotecontrol.server.ServerUdp;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.Arrays;

public class ActionController {
    private Robot robot;
    private ServerUdp serverUdp;

    public ActionController(ServerUdp serverUdp) {
        try {
            robot = new Robot();
            this.serverUdp = serverUdp;
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }



    //from udp
    public void performAction(DatagramPacket packet) throws InterruptedException {
        byte[] actionBytes = packet.getData();
        System.out.println(Arrays.toString(actionBytes));
        //Mouse action
        Thread actionThread = null;
        if (actionBytes[0] == Actions.CONNECTION_ACTION) {
            System.out.println("Connection check");
            actionThread = new Thread(() -> {
                new ConnectionAction(serverUdp, packet, actionBytes).performAction();
            });
        } else if (actionBytes[0] == Actions.MOUSE_KEY_ACTION) {
            actionThread = new Thread(() -> {
                new MouseAction(robot, actionBytes).performAction();
            });
        } else if (actionBytes[0] == Actions.MOUSE_MOVE_ACTION) {
            actionThread = new Thread(() -> {
                new MouseAction(robot, actionBytes).performAction();
            });
        }else if (actionBytes[0] == Actions.VOLUME_ACTION) {
            actionThread = new Thread(() -> {
                new VolumeAction(actionBytes[1]).performAction();
            });
        }
        actionThread.start();
    }

    //from tcp
    public void performAction(Socket socket) {
        try {
            Thread actionThread = null;
            DataInputStream inStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
            byte[] actionBytes = inStream.readNBytes(5);
            if(actionBytes[0] == Actions.KEYBOARD_ACTION) {
                    actionThread = new Thread(() ->{
                        try {
                            new KeyboardAction(robot,actionBytes).performAction();
                        } catch (UnsupportedActionException e) {
                            System.err.println(e.getMessage());
                            try {
                                outStream.writeUTF(e.getMessage());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
            } else if(actionBytes[0] == Actions.MESSAGE_RECIEVED) {

            }
            actionThread.start();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            if (socket != null)
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }


}
