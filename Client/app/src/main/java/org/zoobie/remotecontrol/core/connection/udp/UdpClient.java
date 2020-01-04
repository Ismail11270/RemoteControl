package org.zoobie.remotecontrol.core.connection.udp;

import android.os.AsyncTask;
import android.util.Log;

import org.zoobie.remotecontrol.core.actions.Actions;
import org.zoobie.remotecontrol.core.connection.Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class UdpClient implements Client {

    private Server server;
    private DatagramSocket socket;
    private final String TAG = "client_udp";

    public UdpClient(Server server) {
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.server = server;
    }

    public byte[] receive() throws ExecutionException, InterruptedException {
        return new UdpReciever().execute(socket).get();
    }

    @Override
    public void send(byte... byteData){
        new Thread(() -> {
            try {
                DatagramPacket packet = new DatagramPacket(byteData, byteData.length, server.getIpAdress(), server.getPort());
                socket.send(packet);
            } catch (IOException ex) {
                Log.i(TAG, ex.getMessage());
            }
        }).start();
    }

    @Override
    public boolean checkConnection() throws ExecutionException, InterruptedException {
        System.out.println("checked connection");
        byte[] recieved = ask(Actions.CONNECTION_ACTION,Actions.CONNECTION_CHECK_ACTION);
        System.out.println(Arrays.toString(recieved));
        return recieved[0] == Actions.CONNECTION_CHECK_ACTION;
    }

    public String askServerName() throws ExecutionException, InterruptedException {
        byte[] answer = ask(Actions.CONNECTION_ACTION,Actions.CONNECTION_GET_SERVER_NAME);
        return new String(answer);
    }
    private byte[] ask(byte... data) throws ExecutionException, InterruptedException {
        send(data);
        System.out.println("checked connection");
        return receive();
    }


    public void send(final CharSequence d) {
        send(d.toString().getBytes());
    }

    class UdpReciever extends AsyncTask<DatagramSocket,Void,byte[]> {
        @Override
        protected byte[] doInBackground(DatagramSocket... socket) {
            final byte[] recievedBytes = new byte[64];
            try{
                socket[0].setSoTimeout(2000);
                DatagramPacket recievedPacket = new DatagramPacket(recievedBytes,recievedBytes.length);
                socket[0].receive(recievedPacket);
                recievedBytes[0] = recievedPacket.getData()[0];
                System.out.println(socket[0].getLocalPort() + " " + recievedBytes[0]);
                socket[0].setSoTimeout(0);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
            }

            return recievedBytes;
        }
    }
}
