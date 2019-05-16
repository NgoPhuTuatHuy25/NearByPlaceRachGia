package com.example.aaa;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.example.aaa.dangnhap.DangNhapActivity;

import io.netopen.hotbitmapgg.library.view.RingProgressBar;

public class KhoiChayActivity extends AppCompatActivity {
    RingProgressBar ringProgressBar;
    int ring = 0;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0){
                if(ring<100){
                    ring++;
                    ringProgressBar.setProgress(ring);

                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khoi_chay);

        ringProgressBar = (RingProgressBar) findViewById(R.id.progress_bar_2);
        ringProgressBar.setOnProgressListener(new RingProgressBar.OnProgressListener() {
            @Override
            public void progressToComplete() {
               Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
               startActivity(intent);
              //  Toast.makeText(KhoiChayActivity.this, "",Toast.LENGTH_SHORT).show();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<100; i++){
                    try {
                        Thread.sleep(80);
                        handler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();



    }
}
