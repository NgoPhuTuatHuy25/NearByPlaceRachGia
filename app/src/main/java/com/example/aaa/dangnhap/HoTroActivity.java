package com.example.aaa.dangnhap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.example.aaa.R;

public class HoTroActivity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ho_tro);

        imageView = (ImageView) findViewById(R.id.trove);
    }

    public void trove(View view) {
        Intent intent = new Intent(HoTroActivity.this, KhuVucActivity.class);
        startActivity(intent);
    }
}
