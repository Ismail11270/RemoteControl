package org.zoobie.remotecontrol.core.actions;

import com.profesorfalken.jpowershell.PowerShell;

import java.io.IOException;

public class VolumeAction implements Action {

    private boolean isWindows = false;

    private final byte[] command;
    private Runtime runtime;

    public VolumeAction(byte... command) {
        this.runtime = Runtime.getRuntime();
        if (System.getProperty("os.name").equals("Windows 10")) {
            isWindows = true;
        }
        this.command = command;
    }

    @Override
    public void performActionUdp() {
        performAction();
    }

    @Override
    public void performAction() {
        try {
            switch (command[1]) {
                case Actions.VOLUME_DOWN_ACTION:
                    down();
                    break;
                case Actions.VOLUME_UP_ACTION:
                    up();
                    break;
            }
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    private void up() throws IOException {
        if (isWindows)
            runtime.exec(new String[]{"powershell.exe", "(new-object -com wscript.shell).SendKeys([char]175)"});
    }

    private void down() throws IOException {
        if (isWindows)
            runtime.exec(new String[]{"powershell.exe", "(new-object -com wscript.shell).SendKeys([char]174)"});

    }

    private void mute() throws IOException {
        if (isWindows)
            runtime.exec(new String[]{"powershell.exe", "(new-object -com wscript.shell).SendKeys([char]173)"});
    }
}
