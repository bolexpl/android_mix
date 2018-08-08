package com.example.bolek.testy.activities;

import java.io.File;
import java.io.IOException;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bolek.testy.R;

public class MusicPlayerActivity extends AppCompatActivity implements Runnable, OnClickListener, SeekBar.OnSeekBarChangeListener {

    MediaPlayer mp;
    SeekBar seekBar;
    Thread thread;
    volatile boolean running = false;
    File f;
    TextView t;

    //TODO odtwarzanie przy obracaniu
    //TODO layout
    //TODO service

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop();
                finish();
            }
        });
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        t = (TextView) findViewById(R.id.textview);
        Button play = (Button) findViewById(R.id.play);
        Button pause = (Button) findViewById(R.id.pause);
        Button stop = (Button) findViewById(R.id.stop);

        init();

        seekBar.setMax(mp.getDuration());

        play.setOnClickListener(this);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pause();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });
        seekBar.setOnSeekBarChangeListener(this);
    }

    private void init() {
        init(0);
    }

    private void init(int position) {
        f = (File) getIntent().getExtras().getSerializable("file");
        t.setText(f.getName());
        mp = new MediaPlayer();
        try {
            Uri uri = Uri.parse("file://" + f.getAbsolutePath());
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setDataSource(getApplicationContext(), uri);
            mp.prepare();
            mp.seekTo(position);
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Błąd odczytu pliku!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        play();
    }

    @Override
    public void onBackPressed() {
        mp.stop();
        super.onBackPressed();
    }

    @Override
    public void run() {
        int currentPosition = 0;
        int total = mp.getDuration();
        while (mp != null && currentPosition < total && running) {
            try {
                currentPosition = mp.getCurrentPosition();
                seekBar.setProgress(currentPosition);
                Log.d("wątek", "jestem");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d("wątek", "umieram");
    }

    private void play() {
        mp.start();
        if (thread == null || !running) {
            thread = new Thread(this);
            thread.start();
        }
        running = true;
    }

    private void pause() {
        if (mp.isPlaying()) {
            mp.pause();
            running = false;
        }
    }

    private void stop() {
        running = false;
        try {
            mp.stop();
            mp.prepare();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Błąd", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        mp.seekTo(0);
        seekBar.setProgress(0);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mp.seekTo(seekBar.getProgress());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("playing", mp.isPlaying());
        outState.putInt("position", mp.getCurrentPosition());
        mp.stop();
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        init(savedInstanceState.getInt("position"));
        if (savedInstanceState.getBoolean("playing")) {
            play();
        }
    }
}
