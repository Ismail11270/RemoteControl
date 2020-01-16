package org.zoobie.remotecontrol.core.actions;

import java.awt.event.KeyEvent;
import java.util.HashMap;

//todo add support for special characters
/**
 * Action codes, identical storing identical to the client action codes
 */
public abstract class Actions {

    //Mouse Actions
    public static final byte MOUSE_ACTION = 100;
    public static final byte MOUSE_CLICK_ACTION = 1;
    public static final byte MOUSE_MOVE_ACTION = 2;
    public static final byte MOUSE_SCROLL_ACTION = 3;
    public static final byte MOUSE_DOWN_ACTION = 4;
    public static final byte MOUSE_UP_ACTION = 5;

    public static final byte MESSAGE_RECIEVED = 42;

    //Volume actions
    public static final byte VOLUME_ACTION = 50;
    public static final byte VOLUME_UP_ACTION = 1;
    public static final byte VOLUME_DOWN_ACTION = 2;
    public static final byte VOLUME_MUTE_ACTION = 3;
    //todo
    public static final byte VOLUME_SET_ACTION = 55;



    //Media actions
    public static final byte MEDIA_ACTION = 40;
    public static final byte MEDIA_PLAY_PAUSE = 1;
    public static final byte MEDIA_NEXT = 2;
    public static final byte MEDIA_PREVIOUS = 3;


    //keyboard actions
    public static final byte KEYBOARD_ACTION = 30;
    public static final byte SPECIAL_KEY_ACTION_CLICK = 1;
    public static final byte TEXT_KEY_ACTION_CLICK = 2;
    public static final byte SPECIAL_KEY_ACTION_PRESS = 3;
    public static final byte SPECIAL_KEY_ACTION_RELEASE = 4;

    public static class Keys {
        public static final HashMap<Byte, Integer[]> specialKeysMap; //Action code vs awt keycode
        public static final HashMap<Character, Integer> characterKeysMap;

        static {
            specialKeysMap = new HashMap<>();
            characterKeysMap = new HashMap<>();
            specialKeysMap.put((byte) 0, new Integer[]{KeyEvent.VK_CAPS_LOCK});
            specialKeysMap.put((byte) 1, new Integer[]{KeyEvent.VK_SHIFT});

            //Adding functional keys
            for (int i = 0x70, j = 2; i <= 0x7B; i++,j++) {
                specialKeysMap.put((byte)j,new Integer[]{i});
            }
            specialKeysMap.put((byte) 14, new Integer[]{KeyEvent.VK_ENTER});
            specialKeysMap.put((byte) 15, new Integer[]{KeyEvent.VK_CONTEXT_MENU});
            specialKeysMap.put((byte) 16, new Integer[]{KeyEvent.VK_CONTROL});
            specialKeysMap.put((byte) 17, new Integer[]{KeyEvent.VK_ALT});
            specialKeysMap.put((byte) 18, new Integer[]{KeyEvent.VK_WINDOWS});
            specialKeysMap.put((byte) 19, new Integer[]{KeyEvent.VK_CONTROL, KeyEvent.VK_C});
            specialKeysMap.put((byte) 20, new Integer[]{KeyEvent.VK_CONTROL, KeyEvent.VK_V});
            specialKeysMap.put((byte) 21, new Integer[]{KeyEvent.VK_SPACE});
            specialKeysMap.put((byte) 22, new Integer[]{KeyEvent.VK_CONTROL, KeyEvent.VK_SHIFT, KeyEvent.VK_ESCAPE});
            specialKeysMap.put((byte) 23, new Integer[]{KeyEvent.VK_BACK_SPACE});
            specialKeysMap.put((byte) 24, new Integer[]{KeyEvent.VK_DOWN});
            specialKeysMap.put((byte) 25, new Integer[]{KeyEvent.VK_UP});
            specialKeysMap.put((byte) 26, new Integer[]{KeyEvent.VK_RIGHT});
            specialKeysMap.put((byte) 27, new Integer[]{KeyEvent.VK_LEFT});
            specialKeysMap.put((byte) 28, new Integer[]{KeyEvent.VK_ALT, KeyEvent.VK_F4});
            specialKeysMap.put((byte) 29, new Integer[]{KeyEvent.VK_WINDOWS, KeyEvent.VK_D});
            specialKeysMap.put((byte) 30, new Integer[]{KeyEvent.VK_ALT, KeyEvent.VK_TAB});
            specialKeysMap.put((byte) 31, new Integer[]{KeyEvent.VK_ESCAPE});
            specialKeysMap.put((byte)32, new Integer[]{KeyEvent.VK_TAB});
            specialKeysMap.put((byte)33, new Integer[]{KeyEvent.VK_PAUSE});

            //adding numeric keys
            for (int i = 0x30; i <= 0x39; i++) {
                characterKeysMap.put((char) i, i);
            }

            //adding alphabetic keys
            for (int i = 0x41; i <= 0x5A; i++) {
                characterKeysMap.put((char)(i+32), i);
            }
            characterKeysMap.put('.', KeyEvent.VK_PERIOD);
            characterKeysMap.put(',', KeyEvent.VK_COMMA);
            characterKeysMap.put('-', KeyEvent.VK_MINUS);
            characterKeysMap.put('"', KeyEvent.VK_QUOTE);
            characterKeysMap.put('\'', KeyEvent.VK_QUOTE);

        }
        public static int getCharKeyCode(char key){
            return characterKeysMap.getOrDefault(key, KeyEvent.VK_SPACE);
        }
    }


    //Connection actions
    public static final byte CONNECTION_ACTION = 127;
    public static final byte CONNECTION_CHECK_ACTION = 1;
    public static final byte CONNECTION_GET_SERVER_NAME = 2;
}
