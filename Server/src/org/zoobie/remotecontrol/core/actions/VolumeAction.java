package org.zoobie.remotecontrol.core.actions;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellResponse;

public class VolumeAction implements Action {

    private boolean isWindows = false;

    private final byte[] command;

    public VolumeAction(byte... command) {
        if (System.getProperty("os.name").equals("Windows 10")) {
            isWindows = true;
        }
        this.command = command;
    }

    @Override
    public void performAction() {
        StringBuilder commandSb = new StringBuilder("(new-object -com wscript.shell).SendKeys([char]");
        switch (command[0]) {
            case Actions.VOLUME_DOWN_ACTION:
                down();
                break;
            case Actions.VOLUME_UP_ACTION:
                up();
                break;
            case Actions.VOLUME_MUTE_ACTION:

                break;
            case Actions.VOLUME_SET_ACTION:

                break;
        }
    }

    private void up() {
        if (isWindows)
            PowerShell.executeSingleCommand("(new-object -com wscript.shell).SendKeys([char]175)");
    }

    private void down() {
        if (isWindows)
            PowerShell.executeSingleCommand("(new-object -com wscript.shell).SendKeys([char]174)");
    }

    private void mute() {
        if (isWindows)
            PowerShell.executeSingleCommand("(new-object -com wscript.shell).SendKeys([char]173)");
    }
}
