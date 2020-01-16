package org.zoobie.remotecontrol.core.actions;

import org.zoobie.remotecontrol.server.ServerUdp;

import java.awt.*;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class ActionController {
    private Robot robot;
    private ServerUdp serverUdp;
    private Runtime runtime;
    private static ArrayList<Locale> supportedLanguageLocales;

    static {
        supportedLanguageLocales = new ArrayList<>();
        supportedLanguageLocales.add(Locale.US);
    }

    public ActionController(ServerUdp serverUdp) {
        try {
            robot = new Robot();
            runtime = Runtime.getRuntime();
            this.serverUdp = serverUdp;
        } catch (AWTException e) {
            System.err.println("Robot initialization error!");
        }
    }


    //from udp
    public void performAction(DatagramPacket packet) {
        byte[] actionBytes = packet.getData();

        //Mouse action
        Thread actionThread = null;
        if (actionBytes[0] == Actions.CONNECTION_ACTION) {
            System.out.println("Connection action...");
            actionThread = new Thread(() -> {
                new ConnectionAction(serverUdp, packet, actionBytes).performAction();
            });
        } else if (actionBytes[0] == Actions.MOUSE_ACTION) {
            System.out.println("Mouse action...");
            actionThread = new Thread(() -> {
                new MouseAction(robot, actionBytes).performAction();
            });
        } else if (actionBytes[0] == Actions.VOLUME_ACTION) {
            System.out.println("Volume action...");
            actionThread = new Thread(() -> {
                new VolumeAction(actionBytes).performAction();
            });
        } else if (actionBytes[0] == Actions.KEYBOARD_ACTION) {
            System.out.println("Keyboard action...");
            actionThread = new Thread(() -> {
                try {
                    new KeyboardAction(robot, supportedLanguageLocales, actionBytes).performAction();
                } catch (UnsupportedActionException e) {
                    System.err.println("Current language is not supported!! \nPls change the language and restart the server.");
                }
            });
        } else if (actionBytes[0] == Actions.MEDIA_ACTION) {
            System.out.println("Media action...");
            actionThread = new Thread(() -> {
                new MediaAction(actionBytes).performAction();
            });
        }
        try {
            actionThread.start();
        }catch(NullPointerException e){
            System.err.println("Unknown action!!");
        }
    }
}