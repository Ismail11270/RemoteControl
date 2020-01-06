package org.zoobie.remotecontrol.core.actions;


import android.content.res.Resources;

import org.zoobie.pomd.remotecontrol.R;
import org.zoobie.remotecontrol.MainActivity;

import java.util.HashMap;

/**
 * Action codes 900+, unique
 */
public class Actions {
    public static final byte MOUSE_ACTION = 100;
    public static final byte MOUSE_KEY_ACTION = 101;
    public static final byte MOUSE_DOWN_ACTION = 1;
    public static final byte MOUSE_UP_ACTION = 2;
    public static final byte MOUSE_MOVE_ACTION = 102;
    public static final byte MOUSE_SCROLL_ACTION = 103;

    //keyboard actions
    public static final byte KEYBOARD_ACTION = 30;
    public static final byte SPECIAL_KEY_ACTION_CLICK = 1;
    public static final byte TEXT_KEY_ACTION_CLICK = 2;
    public static final byte SPECIAL_KEY_ACTION_PRESS = 3;
    public static final byte SPECIAL_KEY_ACTION_RELEASE = 4;

    public static class Keys{
        private HashMap<String,Byte> keyActionsMap;
        private Resources res;
        public Keys(String[] tags) {

        }

        public Keys(Resources res) {
            this.res = res;
            String[] tags = res.getStringArray(R.array.tags);
            keyActionsMap = new HashMap<>();
            byte b = 0;
            for(String tag : tags){
                keyActionsMap.put(tag,b);
                b++;
            }
        }

        public Byte getActionCodeForKey(int id){
            return keyActionsMap.get(res.getString(id));
        }

        public Byte getActionCodeForKey(String keyTag){
            return keyActionsMap.get(keyTag);
        }
    }

    //Volume actions
    public static final byte VOLUME_ACTION = 50;
    public static final byte VOLUME_UP_ACTION = 1;
    public static final byte VOLUME_DOWN_ACTION = 2;
    public static final byte VOLUME_MUTE_ACTION = 3;
    //todo
    public static final byte VOLUME_SET_ACTION = 55;


    //Connection actions
    public static final byte CONNECTION_ACTION = 127;
    public static final byte CONNECTION_CHECK_ACTION = 1;
    public static final byte CONNECTION_GET_SERVER_NAME = 2;
}
