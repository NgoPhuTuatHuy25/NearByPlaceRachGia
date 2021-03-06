package com.example.aaa.remote;

import com.example.aaa.model.MyPlaces;

import com.example.aaa.model.PlaceDetail;
import com.example.aaa.pojo.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface IGoogleApiServer {
    @GET
    Call<MyPlaces> getMyPlace(@Url String url);

    @GET
    Call<MyPlaces> getMyNearByPlaces(@Url String url);

    @GET
    Call<PlaceDetail> getXemPlace(@Url String url);

    @GET("maps/api/directions/json")
    Call<String> getDirections(@Query("origin") String origin, @Query("destination") String destination, @Query("key") String key);

    @GET("api/directions/json?key=AIzaSyCEVdf5xZ93P21BJIqCxZVGDjpdSo7iVqI")
    Call<Example> getDistanceDuration(@Query("units") String units, @Query("origin") String origin, @Query("destination") String destination, @Query("mode") String mode);
}
