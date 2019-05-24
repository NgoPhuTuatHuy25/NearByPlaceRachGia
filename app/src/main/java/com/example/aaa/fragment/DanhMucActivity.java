package com.example.aaa.fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.aaa.R;

public class DanhMucActivity extends AppCompatActivity {
    String danhmuc[] = {"Cafe", "Trường học", "Ngân hàng", "Nhà hàng", "Cây xăng", "Mua xăng", "Siêu thị",
            "Bệnh viện", "Nhà thờ", "Nhà sách", "Rạp chiếu phim"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_muc);

        ListView listView = (ListView) findViewById(R.id.listView_danhmuc);
        ArrayAdapter<String> a = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, danhmuc);
        listView.setAdapter(a);

    }
}
