package org.zoobie.remotecontrol.core.actions;

import org.zoobie.remotecontrol.server.Server;

import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Arrays;

public class ActionController {
    private Robot robot;
    private Server server;

    public ActionController(Server server) {
        try {
            robot = new Robot();
            this.server = server;
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void performAction(DatagramPacket packet) throws InterruptedException {
        byte[] actionBytes = packet.getData();
        System.out.println(Arrays.toString(actionBytes));
        //Mouse action
        Thread actionThread = null;
        if(actionBytes[0] == Actions.CONNECTION_ACTION){
            System.out.println("Connection check");
            actionThread = new Thread(() ->{
                new ConnectionAction(server,packet,actionBytes).performAction();
            });
        }
        else if (actionBytes[0] == Actions.MOUSE_KEY_ACTION) {
            actionThread = new Thread(()->{
                new MouseAction(robot,actionBytes[1]).performAction();
            });
        } else if(actionBytes[0] == Actions.TEXT_INPUT_ACTION){
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
