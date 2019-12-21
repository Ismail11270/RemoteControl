package org.zoobie.remotecontrol.core.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TcpClient implements Client{
    private Server server;
    private Socket socket;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;


    public TcpClient(Server server) throws IOException {
        socket = new Socket(server.getIpAdress(),server.getTcpPort());
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
        this.server = server;
    }

    @Override
    public boolean checkConnection() {
        return true;
    }

    @Override
    public void send(byte... data) {
        System.out.println("SENDING");
        if(socket!=null && outputStream!=null){
            try {
                outputStream.write(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("SENT");
    }
}
