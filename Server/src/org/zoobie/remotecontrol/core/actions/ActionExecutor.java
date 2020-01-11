package org.zoobie.remotecontrol.core.actions;

import org.zoobie.remotecontrol.server.Server;

import java.awt.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.Locale;

public class ActionExecutor{
    private Server server;
    Robot mouseRobot, keyboardRobot;
    public static ArrayList<Locale> supportedLanguageLocales;
    static {
        supportedLanguageLocales = new ArrayList<>();
        supportedLanguageLocales.add(Locale.US);
        supportedLanguageLocales.add(Locale.ENGLISH);
        supportedLanguageLocales.add(Locale.UK);
    }

    public ActionExecutor(Server server){
        this.server = server;
        try {
            keyboardRobot = new Robot();
            mouseRobot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void execute(byte[] action){
        new Thread(new Executor(action)).start();
    }

    class Executor implements Runnable{
        private byte[] action;

        Executor(byte[] action){
            this.action = action;
        }

        @Override
        public void run() {
            switch(action[0]){
                case Actions.CONNECTION_ACTION:
                    new ConnectionAction(server,action).performAction();
                    break;
                case Actions.MOUSE_ACTION:
                    new MouseAction(mouseRobot,action).performAction();
                    break;
                case Actions.VOLUME_ACTION:
                    new VolumeAction(action).performAction();
                    break;
                case Actions.KEYBOARD_ACTION:
                    new KeyboardAction(keyboardRobot,supportedLanguageLocales,action);
                    break;
            }
        }
    }
}
