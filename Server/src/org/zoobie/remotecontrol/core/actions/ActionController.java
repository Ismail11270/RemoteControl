package org.zoobie.remotecontrol.core.actions;

import org.zoobie.remotecontrol.server.Server;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Arrays;

public class ActionController {
    Robot robot;
    Server server;

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
        if(actionBytes[0] == Actions.CONNECTION_CHECK_ACTION){
            try {
                server.reply(packet, new byte[]{1});
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (actionBytes[0] == Actions.MOUSE_KEY_ACTION) {
            switch (actionBytes[1]) {
                case Actions.MouseKeyAction.MOUSE_LEFT:
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    break;
                case Actions.MouseKeyAction.MOUSE_MID:
                    robot.mousePress(MouseEvent.BUTTON2_DOWN_MASK);
                    robot.mouseRelease(MouseEvent.BUTTON2_DOWN_MASK);
                    break;
                case Actions.MouseKeyAction.MOUSE_RIGHT:
                    robot.mousePress(MouseEvent.BUTTON3_DOWN_MASK);
                    robot.mouseRelease(MouseEvent.BUTTON3_DOWN_MASK);
                    break;
            }
        }
//        String actionData[] = new String(actionBytes).split(Actions.ACTION_SEPERATOR);
//        int actionCode = Integer.parseInt(actionData[0]);
//        System.out.println(actionData[1]+"x");
//        if(actionCode == Actions.MOUSE_KEY_ACTION) {
//            int keyCode = Integer.parseInt(actionData[1]);
//
//            if(keyCode == Actions.MouseKeyAction.MOUSE_LEFT) {
//                robot.mousePress(MouseEvent.BUTTON1);
//                Thread.sleep(50);
//                robot.mouseRelease(MouseEvent.BUTTON1);
//            }
//        }
    }
}
