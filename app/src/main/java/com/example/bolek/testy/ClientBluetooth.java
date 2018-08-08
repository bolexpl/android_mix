package com.example.bolek.testy;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class ClientBluetooth extends Thread {
    private final BluetoothDevice bDevice;
    private final BluetoothSocket bSocket;

    public ClientBluetooth(BluetoothDevice device){
        BluetoothSocket tmp = null;
        bDevice = device;
        try{
            UUID uuid = UUID.fromString("551e8200-e29c-41d4-a756-446655444657");
            tmp = device.createRfcommSocketToServiceRecord(uuid);
        }catch (IOException e){
            e.printStackTrace();
        }
        bSocket = tmp;
    }

    public void run(){
        try{
            Log.d("client","Próba połączenia...");
            bSocket.connect();
            Log.d("client","Połączono z serwerem!");
            BufferedReader in = new BufferedReader(new InputStreamReader(bSocket.getInputStream()));
            String input = in.readLine();
            Log.d("debug","Serwer mówi: "+input);
        }catch (Exception e){
            try{
                bSocket.close();
            }catch (IOException e2){
                e2.printStackTrace();
            }
        }
    }
}
