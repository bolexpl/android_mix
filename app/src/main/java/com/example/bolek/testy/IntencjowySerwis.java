package com.example.bolek.testy;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class IntencjowySerwis extends IntentService {
    private int i = 0;
    private Handler h = new Handler();

    public IntencjowySerwis() {
        super("Serwis");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        while (i < 10) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            h.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "serwis nr. " + i, Toast.LENGTH_SHORT).show();
                }
            });
            i++;
        }
    }
}
