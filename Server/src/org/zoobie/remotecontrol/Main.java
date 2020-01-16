package org.zoobie.remotecontrol;

import org.zoobie.remotecontrol.server.ServerUdp;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException, AWTException, InterruptedException, LineUnavailableException {
        ServerUdp serverUdp = null;
            for (int i = 1711; i < 5000; i++) {
                try {
                    serverUdp = new ServerUdp(i);
                    serverUdp.listen();
                    break;
                }catch(Exception e){
                    continue;
                }
        }
        if(serverUdp!=null) {
            System.out.println("Udp Server started at " + serverUdp.getIp() + ":" + serverUdp.getPort());
        } else System.err.println("Error starting the server!");
//        MouseInfo.getPointerInfo().getDevice().se
    }

//        ProcessBuilder processBuilder = new ProcessBuilder("notepad.exe");
//        new ProcessBuilder("","fa");
//        Process process = processBuilder.start();
//        PowerShellResponse response = PowerShell.executeSingleCommand("(new-object -com wscript.shell).SendKeys([char]174)");
//        System.out.println(0x74);

}


