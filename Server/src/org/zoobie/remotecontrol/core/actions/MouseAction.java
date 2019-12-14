package org.zoobie.remotecontrol.core.actions;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MouseAction implements Action {

    private Robot robot;
    private final byte[] command;
    private int keyCode;
    public MouseAction(Robot robot, byte... command) {
        this.robot = robot;
        this.command = command;
    }


    @Override
    public void performAction() {
        if(command[0] >= 1 && command[0] <= 3) {
            keyCode = (int)Math.pow(2,9+command[0]);
            performClick();
        }
    }

    private void performClick(){
        System.out.println(keyCode);
        robot.mousePress(keyCode);
        robot.mouseRelease(keyCode);
    }


}
