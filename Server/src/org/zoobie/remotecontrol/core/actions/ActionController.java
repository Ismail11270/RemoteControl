package org.zoobie.remotecontrol.core.actions;

import org.zoobie.remotecontrol.server.ServerUdp;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Socket;
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
            e.printStackTrace();
        }
    }



    //from udp
    public void performAction(DatagramPacket packet) {
        byte[] actionBytes = packet.getData();
        System.out.println(Arrays.toString(actionBytes));
        //Mouse action
        Thread actionThread = null;
        if (actionBytes[0] == Actions.CONNECTION_ACTION) {
            System.out.println("Connection check");
            actionThread = new Thread(() -> {
                new ConnectionAction(serverUdp, packet, actionBytes).performAction();
            });
        } else if (actionBytes[0] == Actions.MOUSE_ACTION) {
            actionThread = new Thread(() -> {
                new MouseAction(robot, actionBytes).performAction();
            });
        } else if (actionBytes[0] == Actions.VOLUME_ACTION) {
            actionThread = new Thread(() -> {
                new VolumeAction(actionBytes).performAction();
            });
        } else if(actionBytes[0] == Actions.KEYBOARD_ACTION){
            actionThread = new Thread(()->{
                try {
                    new KeyboardAction(robot,supportedLanguageLocales,actionBytes).performAction();
                } catch (UnsupportedActionException e) {
                    e.printStackTrace();
                }
            });
        }
        actionThread.start();
    }

}
