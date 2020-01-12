package org.zoobie.remotecontrol.server.udp;

import org.zoobie.remotecontrol.core.actions.*;

import java.awt.*;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class ActionControllerUdp {
    private Robot robot;
    private ServerUdp serverUdp;
    private Runtime runtime;
    public static ArrayList<Locale> supportedLanguageLocales;
    static {
        supportedLanguageLocales = new ArrayList<>();
        supportedLanguageLocales.add(Locale.US);
        supportedLanguageLocales.add(Locale.ENGLISH);
        supportedLanguageLocales.add(Locale.UK);
    }

    public ActionControllerUdp(ServerUdp serverUdp) {
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
        System.out.println("[UDP-server] " + Arrays.toString(actionBytes));
        //Mouse action
        Thread actionThread = null;
        if (actionBytes[0] == Actions.CONNECTION_ACTION) {
            System.out.println("Connection check");
            actionThread = new Thread(() -> {
                new ConnectionAction(serverUdp, packet, actionBytes).performActionUdp();
            });
        } else if (actionBytes[0] == Actions.MOUSE_ACTION) {
            actionThread = new Thread(() -> {
                new MouseAction(robot, actionBytes).performActionUdp();
            });
        } else if (actionBytes[0] == Actions.VOLUME_ACTION) {
            actionThread = new Thread(() -> {
                new VolumeAction(actionBytes).performActionUdp();
            });
        } else if(actionBytes[0] == Actions.KEYBOARD_ACTION){
            actionThread = new Thread(()->{
                    new KeyboardAction(robot,supportedLanguageLocales,actionBytes).performActionUdp();
            });
        }
        actionThread.start();
    }

}
