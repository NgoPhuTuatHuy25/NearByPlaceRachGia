package com.example.aaa;



import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.aaa.fragment.NearByFragment;
import com.example.aaa.fragment.PhanHoiFragment;
import com.example.aaa.fragment.gioiThieuFragment;
import com.example.aaa.fragment.homeFragment;
import com.mancj.materialsearchbar.MaterialSearchBar;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new homeFragment()).commit();
            navigationView.setCheckedItem(R.id.trangchu);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.trangchu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new homeFragment()).commit();
                break;
            case R.id.danduong:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NearByFragment()).commit();
                break;
            case R.id.share:
                shareApp();
                break;
            case R.id.phanhoi:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PhanHoiFragment()).commit();
                break;

            case R.id.gioithieu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new gioiThieuFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void shareApp() {
        String text = "https://github.com/NgoPhuTuatHuy25/quaBongChuyenDong";
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/place");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Đường dẫn đến thư mục ứng dụng");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(Intent.createChooser(intent, "Chọn một ứng dụng để chia sẽ"));
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}