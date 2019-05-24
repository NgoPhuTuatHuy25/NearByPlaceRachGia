package com.example.aaa.dangnhap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.aaa.MainActivity;
import com.example.aaa.R;
import com.example.aaa.dangnhap.KhuVucActivity;

public class KhuVucActivity extends AppCompatActivity {

    ListView listView;
    String thanhpho[] = {"Rạch Giá", "Cần Thơ", "Hồ Chí Minh", "Vũng Tàu", "Đà Nẵng", "Thanh Hóa", "Tây Ninh", "Thừa Thiên Huế","Hà Nội"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khu_vuc);
        listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, thanhpho);
        listView.setAdapter(a);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    Intent intent = new Intent(KhuVucActivity.this, MainActivity.class);
                    // intent.putExtra("thanhpho", listView.getItemAtPosition(position).toString());
                    startActivity(intent);
                }else {
                    startActivity(new Intent(KhuVucActivity.this, HoTroActivity.class));

                }
            }
        });

    }




}
