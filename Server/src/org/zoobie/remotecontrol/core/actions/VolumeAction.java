package org.zoobie.remotecontrol.core.actions;

import com.profesorfalken.jpowershell.PowerShell;
import com.profesorfalken.jpowershell.PowerShellResponse;

public class VolumeAction implements Action{

    PowerShellResponse response = PowerShell.executeSingleCommand("(new-object -com wscript.shell).SendKeys([char]174)");
    private final byte[] command;
    public VolumeAction(byte... command){
        this.command = command;
    }

    @Override
    public void performAction() {
        StringBuilder commandSb = new StringBuilder("(new-object -com wscript.shell).SendKeys([char]");
        switch (command[0]) {
            case Actions.VOLUME_DOWN_ACTION:

                break;
            case Actions.VOLUME_UP_ACTION:

                break;
            case Actions.VOLUME_MUTE_ACTION:

                break;
            case Actions.VOLUME_SET_ACTION:

                break;
        }
    }
}
