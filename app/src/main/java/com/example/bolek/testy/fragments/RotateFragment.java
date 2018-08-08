package com.example.bolek.testy.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bolek.testy.R;

public class RotateFragment extends Fragment {
    public RotateFragment() {
        // Required empty public constructor
    }

    ImageView image;
    Button b;
    TextView size;
    TextView orient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rotate, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        size = (TextView) getActivity().findViewById(R.id.size);
        orient = (TextView) getActivity().findViewById(R.id.orient);

        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                size.setText("small screen");
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                size.setText("normal screen");
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                size.setText("large screen");
                break;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                size.setText("xlarge screen");
                break;
            default:
                size.setText("undefined screen");
        }

        switch (getResources().getConfiguration().orientation){
            case Configuration.ORIENTATION_LANDSCAPE:
                orient.setText("landscape orientation");
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                orient.setText("portrait orientation");
                break;
            default:
                orient.setText("undefined orientation");
        }


        image = (ImageView) getView().findViewById(R.id.imageView2);

        b = (Button) getView().findViewById(R.id.button3);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.setRotation(b.getRotation()+45);
                b.setText(R.string.joke);
            }
        });
    }
}
