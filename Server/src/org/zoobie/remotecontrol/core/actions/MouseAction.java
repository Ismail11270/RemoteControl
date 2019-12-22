package org.zoobie.remotecontrol.core.actions;

import org.zoobie.remotecontrol.Main;

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
        if (command[0] == Actions.MOUSE_KEY_ACTION) {
            if (command[1] >= 1 && command[1] <= 3) {
                keyCode = (int) Math.pow(2, 9 + command[1]);
                performClick();
            }
        } else if (command[0] == Actions.MOUSE_MOVE_ACTION) {
            int x = command[1], y = command[2];
            x *= -1;
            y *= -1;
            moveBy(x,y);
        }
    }

    private void performClick() {
        System.out.println(keyCode);
        robot.mousePress(keyCode);
        robot.mouseRelease(keyCode);
    }

    private void moveBy(int x, int y){
        Point pos = MouseInfo.getPointerInfo().getLocation();
        x += pos.x;
        y += pos.y;
        robot.mouseMove(x, y);
    }

}
