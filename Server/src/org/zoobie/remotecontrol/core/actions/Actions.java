package org.zoobie.remotecontrol.core.actions;

import java.awt.event.KeyEvent;
import java.util.HashMap;


/**
 * Action codes, identical storing identical to the client action codes
 */
public abstract class Actions {

    //non-functional actions


    //Mouse Actions
    public static final byte MOUSE_ACTION = 100;
    public static final byte MOUSE_KEY_ACTION = 101;
    public static final byte MOUSE_MOVE_ACTION = 102;
    public static final byte MOUSE_SCROLL_ACTION = 103;

    public static final byte MESSAGE_RECIEVED = 42;

    //Volume actions
    public static final byte VOLUME_ACTION = 50;
    public static final byte VOLUME_UP_ACTION = 1;
    public static final byte VOLUME_DOWN_ACTION = 2;
    public static final byte VOLUME_MUTE_ACTION = 3;
    //todo
    public static final byte VOLUME_SET_ACTION = 55;

    //keyboard actions
    public static final byte KEYBOARD_ACTION = 30;
    public static final byte SPECIAL_KEY_ACTION_CLICK = 1;
    public static final byte TEXT_KEY_ACTION_CLICK = 2;
    public static final byte SPECIAL_KEY_ACTION_PRESS = 3;
    public static final byte SPECIAL_KEY_ACTION_RELEASE = 4;

    public static class Keys{
        public static final byte ENTER = 1;
        public static final byte BACKSPACE = 2;
        public static final HashMap<Byte,Integer[]> keysMap; //Action code vs awt keycode
        static {
            keysMap = new HashMap<>();
            keysMap.put((byte)0,new Integer[]{KeyEvent.VK_CAPS_LOCK});
            keysMap.put((byte)1,new Integer[]{KeyEvent.VK_SHIFT});
            keysMap.put((byte)2,new Integer[]{KeyEvent.VK_F1});
            keysMap.put((byte)3,new Integer[]{KeyEvent.VK_F2});
            keysMap.put((byte)4,new Integer[]{KeyEvent.VK_F3});
            keysMap.put((byte)5,new Integer[]{KeyEvent.VK_F4});
            keysMap.put((byte)6,new Integer[]{KeyEvent.VK_F5});
            keysMap.put((byte)7,new Integer[]{KeyEvent.VK_F6});
            keysMap.put((byte)8,new Integer[]{KeyEvent.VK_F7});
            keysMap.put((byte)9,new Integer[]{KeyEvent.VK_8});
            keysMap.put((byte)10,new Integer[]{KeyEvent.VK_F9});
            keysMap.put((byte)11,new Integer[]{KeyEvent.VK_F10});
            keysMap.put((byte)12,new Integer[]{KeyEvent.VK_F11});
            keysMap.put((byte)13,new Integer[]{KeyEvent.VK_F12});
            keysMap.put((byte)14,new Integer[]{KeyEvent.VK_ENTER});
            keysMap.put((byte)15,new Integer[]{KeyEvent.VK_CONTEXT_MENU});
            keysMap.put((byte)16,new Integer[]{KeyEvent.VK_CONTROL});
            keysMap.put((byte)17,new Integer[]{KeyEvent.VK_ALT});
            keysMap.put((byte)18,new Integer[]{KeyEvent.VK_WINDOWS});
            keysMap.put((byte)19,new Integer[]{KeyEvent.VK_CONTROL,KeyEvent.VK_C});
            keysMap.put((byte)20,new Integer[]{KeyEvent.VK_CONTROL,KeyEvent.VK_V});
            keysMap.put((byte)21,new Integer[]{KeyEvent.VK_SPACE});
            keysMap.put((byte)22,new Integer[]{KeyEvent.VK_CONTROL,KeyEvent.VK_ALT,KeyEvent.VK_DELETE});
            keysMap.put((byte)23,new Integer[]{KeyEvent.VK_BACK_SPACE});
            keysMap.put((byte)24,new Integer[]{KeyEvent.VK_DOWN});
            keysMap.put((byte)25,new Integer[]{KeyEvent.VK_UP});
            keysMap.put((byte)26,new Integer[]{KeyEvent.VK_RIGHT});
            keysMap.put((byte)27,new Integer[]{KeyEvent.VK_LEFT});
            keysMap.put((byte)28,new Integer[]{KeyEvent.VK_ALT,KeyEvent.VK_F4});
            keysMap.put((byte)29,new Integer[]{KeyEvent.VK_WINDOWS,KeyEvent.VK_D});
            keysMap.put((byte)30,new Integer[]{KeyEvent.VK_ALT,KeyEvent.VK_TAB});
            keysMap.put((byte)31,new Integer[]{KeyEvent.VK_ESCAPE});
        }
    }


    //Connection actions
    public static final byte CONNECTION_ACTION = 127;
    public static final byte CONNECTION_CHECK_ACTION = 1;
    public static final byte CONNECTION_GET_SERVER_NAME = 2;
}
