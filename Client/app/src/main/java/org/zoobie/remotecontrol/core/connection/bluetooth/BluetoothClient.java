package org.zoobie.remotecontrol.core.connection.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.util.Log;

import org.zoobie.remotecontrol.core.actions.Actions;
import org.zoobie.remotecontrol.core.connection.Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class BluetoothClient implements Client {
    private static final String TAG = "BluetoothClient";
    private static final int NUM_OF_RECIEVABLE_BYTES = 20;
    private BluetoothDevice remoteDevice;
    private InputStream inStream;
    private OutputStream outStream;
    private BluetoothSocket socket;
    private CommunicationService commService;
    private static final UUID BT_UUID = UUID.fromString("02fc3120-2000-3230-1000-02f2afa2111b");

    public BluetoothClient(BluetoothDevice remoteDevice) {
        this.remoteDevice = remoteDevice;
        try {
            socket = remoteDevice.createRfcommSocketToServiceRecord(BT_UUID);
            socket.connect();
            commService = new CommunicationService(outStream);
            inStream = socket.getInputStream();
            outStream = socket.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());

        }
    }


    @Override
    public boolean checkConnection() {
        if (socket == null) {
            return false;
        }
        Log.i(TAG, "Checking bluetooth connection...");
        byte[] recieved = ask(Actions.CONNECTION_ACTION, Actions.CONNECTION_CHECK_ACTION);
        Log.d(TAG, "RECEIVED");
        return recieved[0] == Actions.CONNECTION_CHECK_ACTION;
    }

    private byte[] ask(byte... data) {
        try {
            outStream.write(data);
            Log.d(TAG, "SENDING CONFIRMED");
            outStream.close();
            socket = remoteDevice.createRfcommSocketToServiceRecord(BT_UUID);
            socket.connect();
            inStream = socket.getInputStream();
            outStream = socket.getOutputStream();
            byte[] receivedData = new byte[NUM_OF_RECIEVABLE_BYTES];
            inStream.read(receivedData);
            System.out.println("READ " + Arrays.toString(receivedData));
            return receivedData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void send(byte... data) {
        commService.send(data);
    }

    private class CommunicationService {


        private OutputStream oStream;
        public CommunicationService(OutputStream oStream) throws IOException {
            this.oStream = oStream;
        }

        void send(byte[] data) {
            new SenderService(oStream, data).start();
        }

        private class SenderService extends Thread {
            private OutputStream stream;
            private byte[] data;

            SenderService(OutputStream oStream, byte[] data) {
                stream = oStream;
                this.data = data;
            }

            @Override
            public void run() {
                try {
                    stream.write(data);
                    Log.d(TAG, "SENDING CONFIRMED");
                    stream.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
            }
        }


    }
}
