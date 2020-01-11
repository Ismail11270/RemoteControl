package org.zoobie.remotecontrol.server;

import java.sql.CallableStatement;

public interface Server {
    String getMachineName();

    void reply(byte[] bytes);
}
