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

    public KeyboardAction(Robot robot, ArrayList<Locale> supportedLanguageLocales, byte... command) {
        this.robot = robot;
        InputContext context = InputContext.getInstance();
        if (!supportedLanguageLocales.contains(context.getLocale())) {
            System.err.println("Current language " + context.getLocale().toString() + " is not supported!");
            this.command = null;
        } else this.command = command;
    }


    @Override
    public void performActionUdp() {
        performAction();
    }

    @Override
    public void performAction() {
        if(command == null) return;
        if (command[1] == Actions.TEXT_KEY_ACTION_CLICK) {
            char c = (char) command[2];
            pressKey(c);
        } else if (command[1] == Actions.SPECIAL_KEY_ACTION_CLICK) {
            pressSpecialKey(command[2], true);
        } else if (command[1] == Actions.SPECIAL_KEY_ACTION_PRESS) {
            int keyCode = pressSpecialKey(command[2], false);
            keysPressed++;
        } else if (command[1] == Actions.SPECIAL_KEY_ACTION_RELEASE) {
            keysPressed--;
        }
    }

    private int pressSpecialKey(byte b, boolean release) {
        Integer[] keyCodes = Actions.Keys.specialKeysMap.get(b);
        System.out.println(Arrays.toString(keyCodes) + " KEYS NEED TO BE PRESSED");
        if (keyCodes.length > 1) {
            for (int i : keyCodes) {
                robot.keyPress(i);
            }
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
