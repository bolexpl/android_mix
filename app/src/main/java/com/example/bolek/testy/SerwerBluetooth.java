package com.example.bolek.testy;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

public class SerwerBluetooth extends Thread {
    private final BluetoothServerSocket serverSocket;

    public SerwerBluetooth(){
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothServerSocket tmp = null;
        try{
            UUID uuid = UUID.fromString("551e8200-e29c-41d4-a756-446655444657");
            tmp = adapter.listenUsingRfcommWithServiceRecord("Usługa witająca",uuid);
        }catch (IOException e){
            e.printStackTrace();
        }
        serverSocket = tmp;
    }

    public void run(){
        Log.d("serwer","Uruchamiam serwer");
        BluetoothSocket socket = null;
        while(true){
            try{
                Log.d("serwer","Uruchamiam serwer");
                socket = serverSocket.accept();
                Log.d("serwer","Mam klienta!");
                /*Utworzenie strumienia I/O*/
                PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
                out.println("Witaj kolego!");
            }catch (IOException e){
                break;
            }
            if(socket != null){
                try{
                    serverSocket.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
