package com.example.bolek.testy.fragments;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bolek.testy.ClientBluetooth;
import com.example.bolek.testy.R;
import com.example.bolek.testy.SerwerBluetooth;

import java.util.Set;

public class BluetoothFragment extends Fragment {

    public BluetoothFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bluetooth, container, false);
    }

    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
    TextView t1;
    TextView t2;
    EditText et1;
    BluetoothAdapter ba;

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState){
        b1 = (Button) getActivity().findViewById(R.id.button1);
        b2 = (Button) getActivity().findViewById(R.id.button2);
        b3 = (Button) getActivity().findViewById(R.id.button3);
        b4 = (Button) getActivity().findViewById(R.id.button4);
        b5 = (Button) getActivity().findViewById(R.id.button5);
        t1 = (TextView) getActivity().findViewById(R.id.textView1);
        t2 = (TextView) getActivity().findViewById(R.id.textView2);
        et1 = (EditText) getActivity().findViewById(R.id.editText1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dajSieWykryc();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wykryjInne();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pokazSparowane();
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t2.setText("Jo sem serwer");
                new SerwerBluetooth().start();
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t2.setText("Jo sem klient");
                BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
                BluetoothDevice device = ba.getRemoteDevice(et1.getText().toString());
                new ClientBluetooth(device).start();
            }
        });

        ba = BluetoothAdapter.getDefaultAdapter();

        //ba.getAddress() nie działa na nowym androidzie
        String macAddress = android.provider.Settings.Secure.getString(getContext().getContentResolver(), "bluetooth_address");
        t1.setText("Twój mac to: "+macAddress);
        if(!ba.isEnabled()){
            try{
                Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(i, 1);
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent i){
        if(resultCode == Activity.RESULT_OK){
            Log.d("debug","Mamy zgodę");
            ba = BluetoothAdapter.getDefaultAdapter();
            t1.setText("Twój mac to: "+ba.getAddress());
        }
    }

    public void dajSieWykryc(){
        Log.d("debug","Daj się wykryć");
        Intent pokazSie =new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        pokazSie.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,300);
        startActivity(pokazSie);
    }

    private final BroadcastReceiver odbiorca = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent i) {
            String akcja = i.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(akcja)) {
                BluetoothDevice device = i.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String status="";
                if(device.getBondState() != BluetoothDevice.BOND_BONDED){
                    status = "nie sparowane";
                }else{
                    status ="sparowane";
                }
                Toast.makeText(getActivity().getApplicationContext(),"znaleziono urzadzenie: "+device.getName(),Toast.LENGTH_SHORT).show();
                Log.d("debug","znaleziono urzadzenie: "+device.getName()+" - "+device.getAddress());
            }
        }
    };

    public void wykryjInne(){
        Log.d("debug","Szukam innych urządzeń.");
        IntentFilter filtr = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        getActivity().registerReceiver(odbiorca, filtr);
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        ba.startDiscovery();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        try{
            getActivity().unregisterReceiver(odbiorca);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    public void pokazSparowane(){
        Log.d("debug","Pokazuję sparowane");
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> devices = ba.getBondedDevices();
        if(devices.size() >0){
            for(BluetoothDevice device : devices){
                Toast.makeText(getActivity(),device.getName()+" "+device.getAddress(),Toast.LENGTH_SHORT).show();
            }
        }
    }
}
