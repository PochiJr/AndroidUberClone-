package com.example.android.androiduberclone.Service;

import com.example.android.androiduberclone.Common.Common;
import com.example.android.androiduberclone.Model.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by jesus on 06/07/2018.
 */

public class MyFirebaseIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        UpdateTokenToServer(refreshedToken);
    }

    private void UpdateTokenToServer(String refreshedToken) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference tokens = db.getReference(Common.token_tbl);

        Token token = new Token(refreshedToken);

        if (FirebaseAuth.getInstance().getCurrentUser() != null) // Actualizaremos el elemento Token cuando el usuario ya tenga una cuenta
            tokens.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
            .setValue(token);
    }
}
