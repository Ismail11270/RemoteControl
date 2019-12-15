package org.zoobie.remotecontrol.core;

import java.util.Properties;

/**
 * To be deleted
 */
public class Settings {
    private Properties settingsMap;
    private static Settings settings;
    static {
        settings = new Settings();
    }
    private Settings(){
        settingsMap = new Properties();
        settingsMap.put("sens",5.0);
    }

    public static Settings getSettings(){
        return settings;
    }

    public Object getSetting(String name){
        if(settingsMap.contains(name)) return settingsMap.get(name);
        else return null;
    }

    public void setSetting(String name, Object val){
        settingsMap.put(name,val);
    }
}
