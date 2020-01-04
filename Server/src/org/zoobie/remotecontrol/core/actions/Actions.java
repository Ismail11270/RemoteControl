package org.zoobie.remotecontrol.core.actions;

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
    public static final byte SPECIAL_KEY_ACTION = 1;
    public static final byte TEXT_KEY_ACTION = 2;
    public static class Keys{
        public static final byte ENTER = 1;
        public static final byte BACKSPACE = 2;
        public static final byte ARROW_LEFT = 3;
        public static final byte ARROW_UP = 4;
        public static final byte ARROW_DOWN = 5;
        public static final byte ARROW_RIGHT = 6;
        public static final byte ESC = 7;
        public static final byte TAB = 8;
        public static final byte CAPS = 9;
        public static final byte CTRL = 10;
        public static final byte SHIFT = 11;
        public static final byte WINDOWS = 12;
        public static final byte ALT = 13;
        public static final byte SPACE = 14;
        public static final byte FN = 15;

    }


    //Connection actions
    public static final byte CONNECTION_ACTION = 127;
    public static final byte CONNECTION_CHECK_ACTION = 1;
    public static final byte CONNECTION_GET_SERVER_NAME = 2;
}
