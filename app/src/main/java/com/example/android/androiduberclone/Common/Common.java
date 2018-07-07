package com.example.android.androiduberclone.Common;

import android.location.Location;

import com.example.android.androiduberclone.Remote.FCMClient;
import com.example.android.androiduberclone.Remote.IFCMService;
import com.example.android.androiduberclone.Remote.IGoogleAPI;
import com.example.android.androiduberclone.Remote.RetrofitClient;

/**
 * Created by jesus on 26/12/2017.
 */

public class Common {

    public static final String driver_tbl = "Drivers"; // Aquí se almacena la ubicación de los conductores
    public static final String driver_info_tbl = "DriversInformation"; // Aquí se almacenan los datos de los conductores
    public static final String rider_info_tbl = "RidersInformation"; // Aquí se almacenan los datos de los pasajeros
    public static final String pickup_request_tbl = "PickupRequest"; // Aquí se almacenan los datos de las peticiones de recogida
    public static final String token_tbl = "Tokens";

    public static Location mLastLocation = null;


    public static final String baseURL = "https://maps.googleapis.com";
    public static final String fcmURL = "https://fcm.googleapis.com/";

    public static IGoogleAPI getGoogleAPI()
    {
        return RetrofitClient.getClient(baseURL).create(IGoogleAPI.class);
    }

    public static IFCMService getFCMService()
    {
        return FCMClient.getClient(fcmURL).create(IFCMService.class);
    }
}
