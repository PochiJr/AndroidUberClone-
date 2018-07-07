package com.example.android.androiduberclone.Service;

import android.content.Intent;

import com.example.android.androiduberclone.CustomerCall;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

/**
 * Created by jesus on 06/07/2018.
 */

public class MyFirebaseMessaging extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        //Hacemos esto porque el mensaje que recibirá el conductor cuando el cliente lo llame tendrá
        //como parte de información la posición del cliente serializada, así que hay que convertir el mensaje de nuevo en LatLng
        LatLng customer_location = new Gson().fromJson(remoteMessage.getNotification().getBody(),LatLng.class);

        Intent intent = new Intent(MyFirebaseMessaging.this, CustomerCall.class);
        intent.putExtra("lat", customer_location.latitude);
        intent.putExtra("lng",customer_location.longitude);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

    }
}
