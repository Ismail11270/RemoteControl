package org.zoobie.remotecontrol.core.actions;

import javax.sound.sampled.*;

public class VolumeControl extends FloatControl {

    private boolean isWindows = false;
    protected VolumeControl(Type type, float minimum, float maximum, float precision, int updatePeriod, float initialValue, String units) {
        super(type, minimum, maximum, precision, updatePeriod, initialValue, units);
        if(System.getProperty("os.name").equals("Windows 10")){
            isWindows = true;
        }
    }



    private void non() throws LineUnavailableException {
        float volume = 0.1f;
        Mixer.Info[] mixers = AudioSystem.getMixerInfo();
        System.out.println("There are " + mixers.length + " mixer info objects");
        for (Mixer.Info mixerInfo : mixers) {
            if (mixerInfo.getName().contains("Primary Sound Driver")) {
                Mixer mixer = AudioSystem.getMixer(mixerInfo);
                Line.Info[] sourceLineInfo = mixer.getSourceLineInfo();
                Line[] sourceLines = mixer.getTargetLines();
                Line line = mixer.getLine(sourceLineInfo[0]);
                line.open();
                Runtime runtime = Runtime.getRuntime();
                System.out.println(line.getControls().length);
                FloatControl control = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
                System.out.println(control.getValue());
            }
        }
    }
}
