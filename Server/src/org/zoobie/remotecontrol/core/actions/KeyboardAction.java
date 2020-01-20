package org.zoobie.remotecontrol.core.actions;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.im.InputContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class KeyboardAction implements Action {

    public static int keysPressed = 0;
    private final Robot robot;
    private final byte[] command;

    public KeyboardAction(Robot robot, ArrayList<Locale> supportedLanguageLocales, byte... command) throws UnsupportedActionException {
        this.robot = robot;
        this.command = command;
        InputContext context = InputContext.getInstance();
        if (!supportedLanguageLocales.contains(context.getLocale())) {
            throw new UnsupportedActionException("Current language is not supported");
        }
    }


    @Override
    public void performAction() {
        if (command[1] == Actions.TEXT_KEY_ACTION_CLICK) {
            char c = (char) command[2];
            pressKey(c);
        } else if (command[1] == Actions.SPECIAL_KEY_ACTION_CLICK) {
            pressSpecialKey(command[2], true);
        } else if (command[1] == Actions.SPECIAL_KEY_ACTION_PRESS) {
            System.out.println("Key " + command[2] + " toggled");
            int keyCode = pressSpecialKey(command[2], false);
            keysPressed++;
            if (keysPressed > 4) {

            }
        } else if (command[1] == Actions.SPECIAL_KEY_ACTION_RELEASE) {
            release(command[2]);
            System.out.println("Key " + command[2] +" release");
            keysPressed--;
        }
    }
    private int release(byte b){
        Integer[] keyCodes = Actions.Keys.specialKeysMap.get(b);
        System.out.println(keyCodes[0]);
        if (keyCodes.length > 0) {
            for (int i : keyCodes) {
                robot.keyRelease(i);
            }
        }
        return keyCodes[0];
    }
    private int pressSpecialKey(byte b, boolean release) {
        Integer[] keyCodes = Actions.Keys.specialKeysMap.get(b);
        System.out.println(keyCodes[0]);
        if (keyCodes.length > 1) {
            for (int i : keyCodes) {
                robot.keyPress(i);
            }
            if(release)
            for (int i : keyCodes) {
                robot.keyRelease(i);
            }
        } else robot.keyPress(keyCodes[0]);
        if(release)
            robot.keyRelease(keyCodes[0]);
        return keyCodes[0];

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
            robot.keyPress(Actions.Keys.getCharKeyCode(ch));
            robot.keyRelease(Actions.Keys.getCharKeyCode(ch));
            robot.keyRelease(KeyEvent.VK_SHIFT);
        } else {
            robot.keyPress(Actions.Keys.getCharKeyCode(c));
            robot.keyRelease(Actions.Keys.getCharKeyCode(c));
        }
    }
}
