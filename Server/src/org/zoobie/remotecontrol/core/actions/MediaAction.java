package org.zoobie.remotecontrol.core.actions;

import java.io.IOException;

public class MediaAction implements Action{

    private MediaAction.OS os;
    private final byte[] command;
    private Runtime runtime;
    enum OS {
        WINDOWS,LINUX,MACOS
    }
    public MediaAction(byte... command) {
        this.runtime = Runtime.getRuntime();
        String osString = System.getProperty("os.name");
        if (osString.equals("Windows 10")) {
            os = MediaAction.OS.WINDOWS;
        } else if(osString.equals("Linux")) {
            os = OS.LINUX;
        }
        this.command = command;
    }

    @Override
    public void performAction() {
        try {
            if(command[1] == Actions.MEDIA_PLAY_PAUSE){
                playPause();
            }
        }catch(IOException e){
            System.out.println("Unsupported media action...");
        }
    }


    private void playPause() throws IOException {
        if (os== OS.WINDOWS)
            runtime.exec(new String[]{"powershell.exe", "(new-object -com wscript.shell).SendKeys([char]179)"});
        if(os == OS.LINUX){
            System.out.println("Feature not supported on this OS");
        }
    }
}
