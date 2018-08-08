package com.example.bolek.testy.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.bolek.testy.R;

public class AsyncFragment extends Fragment {

    ProgressBar pb;
    Button button;

    private class Watek extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute(){
            Log.d("Nowy","Wątek");
            button.setText(R.string.working);
            pb.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params){
            Log.d("Wiszę","Czekaj");
            try{
                Thread.sleep(3000);
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            Log.d("Nowy","Wątek");
            button.setText(R.string.not_working);
            pb.setVisibility(ProgressBar.INVISIBLE);
        }
    }

    public AsyncFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_async, container, false);
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState){
        pb = (ProgressBar) getView().findViewById(R.id.progressBar3);
        pb.setVisibility(ProgressBar.INVISIBLE);
        button = (Button) getView().findViewById(R.id.thread);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new Watek().execute();
            }
        });

    }
}
