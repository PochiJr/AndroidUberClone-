package com.example.android.androiduberclone.Remote;

import com.example.android.androiduberclone.Model.FCMResponse;
import com.example.android.androiduberclone.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by jesus on 06/07/2018.
 */

public interface IFCMService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA4qfoNm0:APA91bGbPrNnkEgV1viadZf6b-1r9Aex4ApPPd_ohKqeyQ9mB3rqWiwwd5LbOcFzY_jtX9CPZtb8toyiYNkOPu_-dLOqUUANSKDvpBfeEa8S-hPBPmpTd6Y3-Idno4d2s_-a4k5dg2u3"
    })
    @POST("fcm/send")
    Call<FCMResponse> sendMessage(@Body Sender body);
}
