package org.zoobie.remotecontrol.core.actions;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class ActionController {
    Robot robot;
    public ActionController(){
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
    public void performAction(byte[] actionBytes) throws InterruptedException {
        System.out.println(Arrays.toString(actionBytes));
        //Mouse action
        if(Integer.parseInt(new String(Arrays.copyOf(actionBytes,3))) == Actions.MOUSE_KEY_ACTION){
            char key = (char)actionBytes[4];
            System.out.println(key+"");
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
