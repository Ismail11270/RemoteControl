package org.zoobie.remotecontrol.core.actions;

public abstract class Actions {

    //non-functional actions
    public static final byte CONNECTION_CHECK_ACTION = 127;

    public static final byte MOUSE_KEY_ACTION = 101;
    public static final byte MOUSE_MOVE_ACTION = 102;
    public static final byte MOUSE_SCROLL_ACTION = 103;
    public static final byte TEXT_INPUT_ACTION = 90;


    /**
     * Unique for each action
     */
    class MouseKeyAction {
        public static final int MOUSE_LEFT = 1;
        public static final int MOUSE_MID = 2;
        public static final int MOUSE_RIGHT = 3;
    }
}
