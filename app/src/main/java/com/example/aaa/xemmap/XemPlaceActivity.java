package com.example.aaa.xemmap;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aaa.Common;
import com.example.aaa.fragment.homeFragment;
import com.example.aaa.model.PlaceDetail;
import com.example.aaa.chiduong.Results;
import com.squareup.picasso.Picasso;
import com.example.aaa.R;

import com.example.aaa.remote.IGoogleApiServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class XemPlaceActivity extends AppCompatActivity {

    ImageView photo;
    RatingBar ratingBar;
    Button btnViewOnMap,btnViewDirection;
    TextView place_name,place_address,opening_hours, sdt, trangweb;

    IGoogleApiServer mService;

    PlaceDetail mPlace;

    private Results re;
    private double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_place);

        photo = findViewById(R.id.photo);

        ratingBar = findViewById(R.id.ratingBar);

        btnViewOnMap = findViewById(R.id.showmap);
   //     btnViewDirection = findViewById(R.id.chiduong);

        init();
//        Bundle bundle = getIntent().getExtras();
//
//        if (bundle != null) {
//            re = (Results) bundle.getSerializable("result");
//            lat = bundle.getDouble("lat");
//            lng = bundle.getDouble("lng");
//            //Toast.makeText(this, String.valueOf(results.getPhotos()[0].getPhoto_reference()), Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Got Nothing!!", Toast.LENGTH_SHORT).show();
//            return;
//        }

        trangweb = findViewById(R.id.trangweb);
        place_address = findViewById(R.id.places_address);
        place_name = findViewById(R.id.places_name);
        opening_hours = findViewById(R.id.places_open_hour);
        sdt = findViewById(R.id.sodt);

        mService = Common.getIGoogleApiServer();

        place_name.setText("");
        place_address.setText("");
        opening_hours.setText("");
        sdt.setText("");
        trangweb.setText("");

        sdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sodt = sdt.getText().toString().replaceAll("-", "");
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tell:" +sodt));
                startActivity(intent);
            }
        });

        btnViewDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(XemPlaceActivity.this,ChiDuongActivity.class);
//                intent.putExtra("result", re);
//                intent.putExtra("lat", lat);
//                intent.putExtra("lng", lng);
//                intent.putExtra("type", "distance");
                startActivity(intent);
            }
        });

        btnViewOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mPlace.getResult().getUrl()));
                startActivity(mapIntent);
            }
        });

//        if (Common.currresults.getPhotos() != null && Common.currresults.getPhotos().length > 0)  {
//
//            Picasso.with(this)
//                    .load(getPhotoOfPlaces(Common.currresults.getPhotos()[0].getPhoto_reference(),1000))
//                    .placeholder(R.drawable.logoback)
//                    .error(R.drawable.error)
//                    .into(photo);
//        }

        if (Common.currresults.getRating() != null && !TextUtils.isEmpty(Common.currresults.getRating()))  {
            ratingBar.setRating(Float.parseFloat(Common.currresults.getRating()));
        }
        else    {
            ratingBar.setVisibility(View.GONE);
        }

        if (Common.currresults.getOpening_hours() != null)  {
            opening_hours.setText("Open Now : "+Common.currresults.getOpening_hours().getOpen_now());
        }
        else    {
            opening_hours.setVisibility(View.GONE);
        }

        mService.getXemPlace(getPlaceDetailUrl(Common.currresults.getPlace_id()))
                .enqueue(new Callback<PlaceDetail>() {
                    @Override
                    public void onResponse(Call<PlaceDetail> call, Response<PlaceDetail> response) {
                        mPlace = response.body();

                        place_address.setText(mPlace.getResult().getFormatted_address());
                        place_name.setText(mPlace.getResult().getName());
                        sdt.setText(mPlace.getResult().getFormatted_phone_number());
                        trangweb.setText(mPlace.getResult().getWebsite());
                    }

                    @Override
                    public void onFailure(Call<PlaceDetail> call, Throwable t) {

                    }
                });
    }

    private String getPlaceDetailUrl(String place_id) {
        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/details/json");
        url.append("?placeid="+place_id);
        url.append("&key="+getResources().getString(R.string.key_xem_place));

        return url.toString();
    }

    private String getPhotoOfPlaces(String photo_reference,int maxWidth) {

        StringBuilder url = new StringBuilder("https://maps.googleapis.com/maps/api/place/photo");
        url.append("?maxwidth="+maxWidth);
        url.append("&photoreference="+photo_reference);
        url.append("&key="+getResources().getString(R.string.browser_key));

        return url.toString();
    }

    public void trove(View view) {
        startActivity(new Intent(getApplicationContext(), homeFragment.class));
    }
    private void init(){
        btnViewDirection =findViewById(R.id.chiduong);
    }
}
