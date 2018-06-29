package com.example.android.androiduberclone.Common;

import com.example.android.androiduberclone.Remote.IGoogleAPI;
import com.example.android.androiduberclone.Remote.RetrofitClient;

/**
 * Created by jesus on 26/12/2017.
 */

public class Common {
    public static final String baseURL = "https://maps.googleapis.com";
    public static IGoogleAPI getGoogleAPI()
    {
        return RetrofitClient.getClient(baseURL).create(IGoogleAPI.class);
    }
}
