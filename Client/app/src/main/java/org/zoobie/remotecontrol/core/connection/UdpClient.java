package org.zoobie.remotecontrol.core.connection;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutionException;

public class UdpClient {

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

    public void sendData(byte[] byteData){
        new Thread(() -> {
            try {
                DatagramPacket packet = new DatagramPacket(byteData, byteData.length, server.getIpAdress(), server.getUdpPort());
                socket.send(packet);
            } catch (IOException ex) {
                Log.i(TAG, ex.getMessage());
            }
        }).start();
    }

    public void sendData(final CharSequence d) {
        sendData(d.toString().getBytes());
    }



    class UdpReciever extends AsyncTask<DatagramSocket,Void,byte[]> {
        @Override
        protected byte[] doInBackground(DatagramSocket... socket) {
            final byte[] recievedBytes = new byte[1];
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
