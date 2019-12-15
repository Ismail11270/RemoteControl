package org.zoobie.remotecontrol.core.actions;

import org.zoobie.remotecontrol.server.ServerUdp;

import java.awt.*;
import java.net.DatagramPacket;
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
        }else if(actionBytes[0] == Actions.MOUSE_MOVE_ACTION){
            actionThread = new Thread(()->{
                new MouseAction(robot,actionBytes).performAction();
            });
        }else if(actionBytes[0] == Actions.TEXT_INPUT_ACTION){
            actionThread = new Thread(()->{
                new KeyboardAction(robot,actionBytes[1]).performAction();
            });
        } else if(actionBytes[0] == Actions.VOLUME_ACTION){
            actionThread = new Thread(() ->{
               new VolumeAction(actionBytes[1]).performAction();
            });
        }
        actionThread.start();
    }



}
