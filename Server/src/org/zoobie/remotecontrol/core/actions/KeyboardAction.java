package org.zoobie.remotecontrol.core.actions;

import java.awt.*;
import java.awt.event.KeyEvent;

public class KeyboardAction implements Action {

    private final Robot robot;
    private final byte[] command;
    public KeyboardAction(Robot robot, byte... command){
        this.robot = robot;
        this.command = command;
    }

    @Override
    public void performAction() {

    }


    private void typeString(String textToType) throws InterruptedException {
        for(char c : textToType.toCharArray()){
            pressKey(c);
        }
    }
    private void pressKey(char c) throws InterruptedException {
        System.out.println(c);
        char ch =Character.toLowerCase(c);
        if(c == '"' || (byte)ch!= (byte)c) {
            robot.keyPress(KeyEvent.VK_SHIFT);
            Thread.sleep(5);
            robot.keyPress(getKey(ch));
            Thread.sleep(10);
            robot.keyRelease(getKey(ch));
            robot.keyRelease(KeyEvent.VK_SHIFT);
        }
        else {
            robot.keyPress(getKey(c));
            Thread.sleep(10);
            robot.keyRelease(getKey(c));
        }
    }

    private static int getKey(char c){
        switch(c){
            case 'a': return KeyEvent.VK_A;
            case 'b': return KeyEvent.VK_B;
            case 'c': return KeyEvent.VK_C;
            case 'd': return KeyEvent.VK_D;
            case 'e': return KeyEvent.VK_E;
            case 'f': return KeyEvent.VK_F;
            case 'g': return KeyEvent.VK_G;
            case 'h': return KeyEvent.VK_H;
            case 'i': return KeyEvent.VK_I;
            case 'j': return KeyEvent.VK_J;
            case 'k': return KeyEvent.VK_K;
            case 'l': return KeyEvent.VK_L;
            case 'm': return KeyEvent.VK_M;
            case 'n': return KeyEvent.VK_N;
            case 'o': return KeyEvent.VK_O;
            case 'p': return KeyEvent.VK_P;
            case 'q': return KeyEvent.VK_Q;
            case 'r': return KeyEvent.VK_R;
            case 's': return KeyEvent.VK_S;
            case 't': return KeyEvent.VK_T;
            case 'u': return KeyEvent.VK_U;
            case 'v': return KeyEvent.VK_V;
            case 'w': return KeyEvent.VK_W;
            case 'x': return KeyEvent.VK_X;
            case 'y': return KeyEvent.VK_Y;
            case 'z': return KeyEvent.VK_Z;
            case '.': return KeyEvent.VK_PERIOD;
            case ',': return KeyEvent.VK_COMMA;
            case '-': return KeyEvent.VK_MINUS;
            case '"':
            case '\'': return KeyEvent.VK_QUOTE;
            default: return KeyEvent.VK_SPACE;

        }
    }
}
