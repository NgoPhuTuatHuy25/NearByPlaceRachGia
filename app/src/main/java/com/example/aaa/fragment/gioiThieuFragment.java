package com.example.aaa.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aaa.R;


public class gioiThieuFragment extends Fragment {
    TextView txtDienThoai;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_gioi_thieu, container, false);

        txtDienThoai = (TextView) view.findViewById(R.id.goi_dien);

        txtDienThoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt = txtDienThoai.getText().toString().replaceAll("-", "");
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tell:" +sdt));
                startActivity(intent);
            }
        });

        return view;
    }

}