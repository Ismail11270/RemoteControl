package org.zoobie.remotecontrol;

import org.zoobie.remotecontrol.server.ServerUdp;

import javax.sound.sampled.*;
import java.awt.*;
import java.io.IOException;

public class Main {

    public static Robot robot;

    public static void main(String[] args) throws IOException, AWTException, InterruptedException, LineUnavailableException {
        ServerUdp serverUdp = new ServerUdp(1711);
        System.out.println("Udp Server started at " + serverUdp.getIp() + ":"+serverUdp.getPort());
        serverUdp.listen();
    }

//        ProcessBuilder processBuilder = new ProcessBuilder("notepad.exe");
//        new ProcessBuilder("","fa");
//        Process process = processBuilder.start();
//        PowerShellResponse response = PowerShell.executeSingleCommand("(new-object -com wscript.shell).SendKeys([char]174)");
//        System.out.println(0x74);

}


