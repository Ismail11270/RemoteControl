package org.zoobie.remotecontrol;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellResponse;
import org.zoobie.remotecontrol.server.Server;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Timer;

public class Main {

    public static Robot robot;

    public static void main(String[] args) throws IOException, AWTException, InterruptedException, LineUnavailableException {
        Server server = new Server();
        server.listen();
    }

//        ProcessBuilder processBuilder = new ProcessBuilder("notepad.exe");
//        new ProcessBuilder("","fa");
//        Process process = processBuilder.start();
//        PowerShellResponse response = PowerShell.executeSingleCommand("(new-object -com wscript.shell).SendKeys([char]174)");
//        System.out.println(0x74);

}


