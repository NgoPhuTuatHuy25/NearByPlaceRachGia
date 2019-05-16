package com.example.aaa.dangnhap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.aaa.R;

public class DangKiActivity extends AppCompatActivity {
    TextView daCoTk, name, mail, sdt, user, pass;
    String stringName, stringMail, stringSdt, stringUser, stringPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        daCoTk = (TextView) findViewById(R.id.txt_dangki);

        name = (TextView) findViewById(R.id.name);
        mail = (TextView) findViewById(R.id.mail);
        sdt = (TextView) findViewById(R.id.phone);
        user = (TextView) findViewById(R.id.user);
        pass = (TextView) findViewById(R.id.pass);
        daCoTk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangKiActivity.this, DangNhapActivity.class));
            }
        });
    }
    public void OnSign(View view){
        stringName = name.getText().toString();
        stringMail = mail.getText().toString();
        stringSdt = sdt.getText().toString();
        stringUser = user.getText().toString();
        stringPass = pass.getText().toString();
        String type = "signup";
        Database database = new Database(this);
        database.execute(type, stringName, stringMail, stringSdt, stringUser, stringPass);
    }
}
