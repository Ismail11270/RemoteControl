package org.zoobie.remotecontrol.core.actions;


/**
 * Action codes 900+, unique
 */
public class Actions {

    public static final byte MOUSE_KEY_ACTION = 101;
    public static final byte MOUSE_MOVE_ACTION = 102;
    public static final byte MOUSE_SCROLL_ACTION = 103;
    public static final byte TEXT_INPUT_ACTION = 90;


    //Volume actions
    public static final byte VOLUME_ACTION = 51;
    public static final byte VOLUME_UP_ACTION = 52;
    public static final byte VOLUME_DOWN_ACTION = 53;
    public static final byte VOLUME_MUTE_ACTION = 54;
    //todo
    public static final byte VOLUME_SET_ACTION = 55;


    //Connection actions
    public static final byte CONNECTION_ACTION = 127;
    public static final byte CONNECTION_CHECK_ACTION = 1;
    public static final byte CONNECTION_GET_SERVER_NAME = 2;
}
