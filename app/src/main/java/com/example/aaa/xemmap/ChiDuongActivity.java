package com.example.aaa.xemmap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.aaa.helper.DirectionJSONParser;
import com.example.aaa.pojo.Example;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;
import com.example.aaa.Common;
import com.example.aaa.R;

import com.example.aaa.remote.IGoogleApiServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChiDuongActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    TextView ShowDistanceDuration;

    FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    LocationRequest locationRequest;
    Location mLastLocation;
    Marker mCurrentMarker;

    Polyline polyline;

    IGoogleApiServer mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_duong);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mService = Common.getIGoogleApiServerScalar();
        ShowDistanceDuration = (TextView) findViewById(R.id.show_distance_time);

        buildLocationRequest();
        buildLocationCallback();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    @Override
    protected void onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        super.onStop();
    }

    private void buildLocationCallback() {
        locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                mLastLocation = locationResult.getLastLocation();
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                        .title("Your Location")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                mCurrentMarker = mMap.addMarker(markerOptions);

                mMap.moveCamera(CameraUpdateFactory
                        .newLatLng(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));

                LatLng destinationLatLng = new LatLng(
                        Double.parseDouble(Common.currresults.getGeometry().getLocation().getLat()),
                        Double.parseDouble(Common.currresults.getGeometry().getLocation().getLng()));

                mMap.addMarker(new MarkerOptions()
                        .position(destinationLatLng)
                        .title(Common.currresults.getName())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

                drawPath(mLastLocation, Common.currresults.getGeometry().getLocation());
            }
        };
    }

    @SuppressLint("RestrictedApi")
    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setSmallestDisplacement(10f);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                mLastLocation = location;
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                        .title("Your Location")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                mCurrentMarker = mMap.addMarker(markerOptions);

                mMap.moveCamera(CameraUpdateFactory
                        .newLatLng(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude())));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(12.0f));

                LatLng destinationLatLng = new LatLng(
                        Double.parseDouble(Common.currresults.getGeometry().getLocation().getLat()),
                        Double.parseDouble(Common.currresults.getGeometry().getLocation().getLng()));

                mMap.addMarker(new MarkerOptions()
                        .position(destinationLatLng)
                        .title(Common.currresults.getName())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

                drawPath(mLastLocation, Common.currresults.getGeometry().getLocation());
            }
        });
    }

    private void drawPath(Location mLastLocation, com.example.aaa.model.Location location) {

        if (polyline != null) {
            polyline.remove();
        }

        String origin = new StringBuilder(
                String.valueOf(mLastLocation.getLatitude()))
                .append(",")
                .append(String.valueOf(mLastLocation.getLongitude()))
                .toString();

        String destination = new StringBuilder(
                location.getLat())
                .append(",")
                .append(location.getLng())
                .toString();
        String key = getResources().getString(R.string.google_maps_key);

        mService.getDirections(origin, destination, key)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        new ParserTask().execute(response.body().toString());


                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
        String url = "https://maps.googleapis.com/maps/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IGoogleApiServer service = retrofit.create(IGoogleApiServer.class);
        Call<Example> call = service.getDistanceDuration("mectric",origin, destination, "driving");
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                try {
                    //Remove previous line from map
                    if (polyline != null) {
                        polyline.remove();
                    }
                    // This loop will go through all the results and add marker on each location.
                    for (int i = 0; i < response.body().getRoutes().size(); i++) {
                        String distance = response.body().getRoutes().get(i).getLegs().get(i).getDistance().getText();
                        String time = response.body().getRoutes().get(i).getLegs().get(i).getDuration().getText();
                        ShowDistanceDuration.setText("Distance:" + distance + ", Duration:" + time);
                        String encodedString = response.body().getRoutes().get(0).getOverviewPolyline().getPoints();

                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }


    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

      //  AlertDialog waitingDialog = new SpotsDialog(ChiDuongActivity.this);

//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//         //   waitingDialog.show();
//       //     waitingDialog.setMessage("chờ");
//
//        }

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... strings) {
            JSONObject jsonObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jsonObject = new JSONObject(strings[0]);

                DirectionJSONParser parser = new DirectionJSONParser();
                routes = parser.parse(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
       //     super.onPostExecute(lists);

          //  ArrayList points = null;
           // PolylineOptions polylineOptions = null;

            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            for (int i = 0; i < lists.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

//            for (int i = 0; i < lists.size(); i++) {
//
//                points = new ArrayList();
//                polylineOptions = new PolylineOptions();
//
//                List<HashMap<String, String>> path = lists.get(i);
//
//                for (int j = 0; j < path.size(); j++) {
//
//                    HashMap<String, String> point = path.get(j);
//
//                    double lat = Double.parseDouble(point.get("lat"));
//                    double lng = Double.parseDouble(point.get("lng"));
//
//                    LatLng position = new LatLng(lat, lng);
                List<HashMap<String, String>> path = lists.get(i);

            // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);

                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }
                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.BLUE);
                lineOptions.geodesic(true);
                Log.d("onPostExecute", "onPostExecute lineoptions decoded");
            }
            if (lineOptions != null) {
                mMap.addPolyline(lineOptions);
              //  waitingDialog.dismiss();

            }

        }
    }
}