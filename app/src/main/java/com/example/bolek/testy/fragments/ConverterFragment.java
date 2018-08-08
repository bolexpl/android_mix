package com.example.bolek.testy.fragments;

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

import com.example.bolek.testy.R;

public class ConverterFragment extends Fragment {

    EditText input;
    Button button;
    TextView binView;
    TextView octView;
    TextView hexView;
    int original;
    int toBin;

    public ConverterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_converter, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState){
        input = (EditText) v.findViewById(R.id.input);
        button = (Button) v.findViewById(R.id.convert);
        binView = (TextView) v.findViewById(R.id.bin);
        octView = (TextView) v.findViewById(R.id.oct);
        hexView = (TextView) v.findViewById(R.id.hex);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    original = Integer.parseInt(input.getText().toString());
                    toBin = original;

                    String wynik="";

                    while(toBin!=0){
                        wynik=Integer.toString(toBin%2)+wynik;
                        toBin/=2;
                        Log.d("wynik",wynik);
                        Log.d("toBin",Integer.toString(toBin));
                    }
                    binView.setText(wynik);

                    octView.setText(Integer.toOctalString(original));
                    hexView.setText(Integer.toHexString(original));

                }catch(NumberFormatException e){
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(),"Zła liczba",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
