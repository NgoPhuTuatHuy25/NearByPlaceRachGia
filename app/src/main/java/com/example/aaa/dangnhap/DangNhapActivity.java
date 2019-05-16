package com.example.aaa.dangnhap;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aaa.R;

public class DangNhapActivity extends AppCompatActivity {
    EditText UsernameEt, PasswordEt;
  //  AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        UsernameEt = (EditText)findViewById(R.id.etUserName);
        PasswordEt = (EditText)findViewById(R.id.etPassword);
    }

    public void OnLogin(View view) {
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String type = "login";
        Database database = new Database(this);
        database.execute(type, username, password);
    }

    //đăng nhập
    public void dangnhap(View view) {
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String type = "login";
        Database database = new Database(this);
        database.execute(type, username, password);

       // startActivity(new Intent(this, KhuVucActivity.class));


    }
    //đăng kí
    public void dangki(View view) {
        startActivity(new Intent(this, DangKiActivity.class));

    }


}
