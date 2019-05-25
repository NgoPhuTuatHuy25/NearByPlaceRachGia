package com.example.aaa.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aaa.Common;
import com.example.aaa.R;
import com.example.aaa.model.MyPlaces;
import com.example.aaa.model.Results;

import com.example.aaa.xemmap.BottomNavigationViewHelper;
import com.example.aaa.xemmap.XemPlaceActivity;
import com.example.aaa.remote.IGoogleApiServer;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class homeFragment extends Fragment implements OnMapReadyCallback
{
    //vung bán kính gps
    private  static final int MY_PER = 1000;
    private GoogleMap mMap;
   // private GoogleApiClient googleApiClient;

    //ví trí mới
    LocationCallback locationCallback;
    FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest mlLocationRequest;

    private  double latitude, longitude;
    private Location mLastLocation;
    private Marker marker;

    IGoogleApiServer mServer;

    EditText placeText;

    MyPlaces myPlaces;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapapi);
        mapFragment.getMapAsync(this);

        mServer = Common.getIGoogleApiServer();
        //kiểm tra kết nối với server
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocaltionPermission();
        }
        ImageButton imageButton = (ImageButton) rootView.findViewById(R.id.danhmuc);

        Spinner spinner = rootView.findViewById(R.id.maptype);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        break;
                    case 1:
                        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        break;
                    case 2:
                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        break;
                    case 3:
                        mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                        break;
                    default:
                        mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) rootView.findViewById(R.id.nav_bott);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.truatm:
                        nearbyPlace("atm");
                        break;
                    case R.id.xebus:
                        nearbyPlace("bus_station");
                        break;
                    case R.id.khachsan:
                        nearbyPlace("lodging");
                        break;
                }

                return true;
            }
        });
        TimViTri();

        //khởi tạo vị trí
        buildLocationRequest();
        buildLocationCallback();

        // hiển thị cái địa điểm liền kề gần nhau
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        //lấy được vị trí thay đổi mỗi khi bạn di chuyển thì,
        // bạn phải đăng ký một request cho location changes thông qua phương thức
        //Looper có một hàm loop() có nhiệm vụ xử lý từng message trong hàng chờ và sẽ block nếu hàng chờ trống.
        fusedLocationProviderClient.requestLocationUpdates(mlLocationRequest,locationCallback, Looper.myLooper());

        return rootView;
    }

    @Override
    public void onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        super.onStop();
    }
    //xây dựng Địa điểm Gọi lại
    private void buildLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mLastLocation = locationResult.getLastLocation();

                if (marker != null) {
                    marker.remove();
                }
                latitude = mLastLocation.getLatitude();
                longitude = mLastLocation.getLongitude();

                LatLng latLng = new LatLng(latitude, longitude);

                MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                        .title("Your");
                //        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                marker = mMap.addMarker(markerOptions);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
            }
        };
    }

    //xây dựng yêu cầu vị trí
    private void buildLocationRequest() {
        mlLocationRequest = new LocationRequest();
        mlLocationRequest.setInterval(1000);
        mlLocationRequest.setFastestInterval(1000);
        mlLocationRequest.setSmallestDisplacement(10f);
        mlLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

    }

    private void TimViTri() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        boolean network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Location location;

        if (network_enabled) {

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                mLastLocation = location;
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                Toast.makeText(
                        getActivity(),
                        String.valueOf(location.getLatitude()) + "\n" + String.valueOf(location.getLongitude()), Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void nearbyPlace(final String placeType) {
        mMap.clear();
        String url = getUrL(latitude,longitude, placeType);
        mServer.getMyPlace(url)
                .enqueue(new Callback<MyPlaces>() {
                    @Override
                    public void onResponse(Call<MyPlaces> call, Response<MyPlaces> response) {
                        myPlaces = response.body(); //nhớ gán giá trị cho myPlace
                        if(response.isSuccessful()){
                            for(int i = 0; i < response.body().getResults().length; i++){
                                MarkerOptions markerOptions = new MarkerOptions();
                                Results googleResults = response.body().getResults()[i];
                                double lat = Double.parseDouble(googleResults.getGeometry().getLocation().getLat());
                                double lng = Double.parseDouble(googleResults.getGeometry().getLocation().getLng());
                                String placeName = googleResults.getName();
                                String vicinicity = googleResults.getVicinity();
                                LatLng latLng = new LatLng(lat, lng);
                                markerOptions.position(latLng);
                                markerOptions.title(placeName);
                                markerOptions.title(vicinicity);
                                if(placeType.equals("atm"))
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

                                else if(placeType.equals("bus_station"))
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                                else  if(placeType.equals("lodging"))
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                else  if(placeType.equals("cafe"))
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                else
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

                                markerOptions.snippet(String.valueOf(i)); //gán chỉ số cho market
                                mMap.addMarker(markerOptions);
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(13));

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MyPlaces> call, Throwable t) {

                    }
                });
    }

    private String getUrL(double latitude, double longitude, String placeType) {
        StringBuilder goBuilder = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        goBuilder.append("location=" + latitude + "," + longitude);
        goBuilder.append("&radius=5000");
        goBuilder.append("&type=" +placeType);
       // goBuilder.append("&keyword=cruise");
       // goBuilder.append("&sensor=true");
        goBuilder.append("&key="+getResources().getString(R.string.browser_key));
        Log.d("getUrL", goBuilder.toString());
        return goBuilder.toString();
    }
    //kiểm tra quyền cục bộ
    private  boolean checkLocaltionPermission(){
        //Các quyền cần người dùng cho phép.
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            //Hiển thị một Dialog hỏi người dùng cho phép các quyền trên.
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                },MY_PER);
            else
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                },MY_PER);
            return false;


        }else
            return true;
    }

    @Override
    // // Khi người dùng trả lời yêu cầu cấp quyền (cho phép hoặc từ chối).
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PER: {
                // Chú ý: Nếu yêu cầu bị bỏ qua, mảng kết quả là rỗng.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        mMap.setMyLocationEnabled(true);
                        buildLocationRequest();
                        buildLocationCallback();

                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
                        fusedLocationProviderClient.requestLocationUpdates(mlLocationRequest,locationCallback, Looper.myLooper());
                    }
                }
            }
            break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //  onLocationChanged(mLastLocation);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
                //Chỉ gọi phương thức này khi đã có quyền xem vị trí người dùng.
                mMap.setMyLocationEnabled(true);
            }
        }else{
            mMap.setMyLocationEnabled(true);
        }
        //sự kiên bấm vào địa điểm
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                if(marker.getSnippet() != null) {
                    //khi người dùng chom địa điểm, thì sẽ server trả kết quả về
                    Common.currresults = myPlaces.getResults()[Integer.parseInt(marker.getSnippet())];
                    startActivity(new Intent(getActivity(), XemPlaceActivity.class));
                }
                //bắt sự kiện màn hình mới
                //  startActivity(new Intent(MapsActivity.this, xemPlace.class));
                return true;
            }
        });
    }
}
