package org.zoobie.remotecontrol.core.actions;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.im.InputContext;
import java.util.ArrayList;
import java.util.Locale;

public class KeyboardAction implements Action {

    private final Robot robot;
    private final byte[] command;
    private static ArrayList<Locale> supportedLanguageLocales;
    static {
        supportedLanguageLocales.add(Locale.US);
    }
    public KeyboardAction(Robot robot, byte... command) throws UnsupportedActionException {
        this.robot = robot;
        this.command = command;
        InputContext context = InputContext.getInstance();
        if(!supportedLanguageLocales.contains(context.getLocale())) {
            throw new UnsupportedActionException("Current language is not supported");
        }
    }

    @Override
    public void performAction() {
        if (command[1] == Actions.KEYBOARD_CHAR_INPUT) {
            char c = (char) command[2];
            if (Character.isUpperCase(c)) {
                pressKey(c);
            }
        }
    }


    private void typeString(String textToType) {
        for (char c : textToType.toCharArray()) {
            pressKey(c);
        }
    }

    private void pressKey(char c) {
        char ch = Character.toLowerCase(c);
        if (Character.isUpperCase(c)) {
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(getKey(ch));
            robot.keyRelease(getKey(ch));
            robot.keyRelease(KeyEvent.VK_SHIFT);
        } else {
            robot.keyPress(getKey(c));
            robot.keyRelease(getKey(c));
        }
    }

    private static int getKey(char c) {
        switch (c) {
            case 'a':
                return KeyEvent.VK_A;
            case 'b':
                return KeyEvent.VK_B;
            case 'c':
                return KeyEvent.VK_C;
            case 'd':
                return KeyEvent.VK_D;
            case 'e':
                return KeyEvent.VK_E;
            case 'f':
                return KeyEvent.VK_F;
            case 'g':
                return KeyEvent.VK_G;
            case 'h':
                return KeyEvent.VK_H;
            case 'i':
                return KeyEvent.VK_I;
            case 'j':
                return KeyEvent.VK_J;
            case 'k':
                return KeyEvent.VK_K;
            case 'l':
                return KeyEvent.VK_L;
            case 'm':
                return KeyEvent.VK_M;
            case 'n':
                return KeyEvent.VK_N;
            case 'o':
                return KeyEvent.VK_O;
            case 'p':
                return KeyEvent.VK_P;
            case 'q':
                return KeyEvent.VK_Q;
            case 'r':
                return KeyEvent.VK_R;
            case 's':
                return KeyEvent.VK_S;
            case 't':
                return KeyEvent.VK_T;
            case 'u':
                return KeyEvent.VK_U;
            case 'v':
                return KeyEvent.VK_V;
            case 'w':
                return KeyEvent.VK_W;
            case 'x':
                return KeyEvent.VK_X;
            case 'y':
                return KeyEvent.VK_Y;
            case 'z':
                return KeyEvent.VK_Z;
            case '.':
                return KeyEvent.VK_PERIOD;
            case ',':
                return KeyEvent.VK_COMMA;
            case '-':
                return KeyEvent.VK_MINUS;
            case '"':
            case '\'':
                return KeyEvent.VK_QUOTE;
            default:
                return KeyEvent.VK_SPACE;

        }
    }
}
