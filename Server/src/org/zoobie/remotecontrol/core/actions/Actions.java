package org.zoobie.remotecontrol.core.actions;

public abstract class Actions {
    public static final int MOUSE_KEY_ACTION = 901;
    public static final int MOUSE_MOVE_ACTION = 902;
    public static final int MOUSE_SCROLL_ACTION = 903;
    public static final int TEXT_INPUT_ACTION = 910;
    public static final String ACTION_SEPERATOR = "@c!i0n";

    class MouseKeyAction {
        public static final int MOUSE_LEFT = 1;
        public static final int MOUSE_MID = 2;
        public static final int MOUSE_RIGHT = 3;
    }
}
