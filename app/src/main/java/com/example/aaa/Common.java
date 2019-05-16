package com.example.aaa;

import com.example.aaa.model.Results;
import com.example.aaa.remote.IGoogleApiServer;
import com.example.aaa.remote.RetrofitClient;
import com.example.aaa.remote.RetrofitScalarsClient;

public class Common {

    public static  Results currresults;
    private static final  String  GOOGLE_API_URL = "https://maps.googleapis.com/";

    public static IGoogleApiServer getIGoogleApiServer(){
        return RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleApiServer.class);
    }
    public static IGoogleApiServer getIGoogleApiServerScalar() {
        return RetrofitScalarsClient.getScalarClient(GOOGLE_API_URL).create(IGoogleApiServer.class);
    }
}
