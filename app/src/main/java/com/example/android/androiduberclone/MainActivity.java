package com.example.android.androiduberclone;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.android.androiduberclone.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn, btnRegister;
    RelativeLayout rootLayout;

    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Arkhip_font.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_main);

        // Inicializar Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        // Inicializar la View
        btnRegister = (Button)findViewById(R.id.btnRegister);
        btnSignIn = (Button)findViewById(R.id.btnSignIn);
        rootLayout = (RelativeLayout)findViewById(R.id.rootLayout);

        // Event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterDialog();
            }
        });
        
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignInDialog();
            }
        });
    }

    private void showSignInDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("INICIAR SESIÓN");
        dialog.setMessage("Introduzca su email para iniciar sesión");

        LayoutInflater inflater = LayoutInflater.from(this);
        View login_layout = inflater.inflate(R.layout.layout_login, null);

        final MaterialEditText edtEmail = login_layout.findViewById(R.id.edtEmail);
        final MaterialEditText edtPassword = login_layout.findViewById(R.id.edtPassword);

        dialog.setView(login_layout);

        // Ponemos el botón
        dialog.setPositiveButton("INICIAR SESIÓN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                        // Comprobar validación
                        if (TextUtils.isEmpty(edtEmail.getText().toString())) {
                            Snackbar.make(rootLayout, "Por favor, introduzca su email",
                                    Snackbar.LENGTH_SHORT)
                                    .show();
                            return;
                        }

                        if (TextUtils.isEmpty(edtPassword.getText().toString())) {
                            Snackbar.make(rootLayout, "Por favor, introduzca su contraseña",
                                    Snackbar.LENGTH_SHORT)
                                    .show();
                            return;
                        }

                        if (edtPassword.getText().toString().length() < 6) {
                            Snackbar.make(rootLayout, "La contraseña no puede contener menos de 6 " +
                                    "caracteres", Snackbar.LENGTH_SHORT)
                                    .show();
                            return;
                        }


                        // Iniciar sesión
                        auth.signInWithEmailAndPassword(edtEmail.getText().toString(),
                                edtPassword.getText().toString())
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        startActivity(new Intent(MainActivity.this,Bienvenido.class));
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Snackbar.make(rootLayout, "Fallo al iniciar sesión" + e.getMessage(),
                                                Snackbar.LENGTH_SHORT);
                                    }
                                });

                    }
                });

        dialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });



        dialog.show();
    }


    private void showRegisterDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("REGISTRARSE");
        dialog.setMessage("Introduzca su email para registrarse");

        LayoutInflater inflater = LayoutInflater.from(this);
        View register_layout = inflater.inflate(R.layout.layout_register, null);

        final MaterialEditText edtEmail = register_layout.findViewById(R.id.edtEmail);
        final MaterialEditText edtPassword = register_layout.findViewById(R.id.edtPassword);
        final MaterialEditText edtName = register_layout.findViewById(R.id.edtName);
        final MaterialEditText edtPhone = register_layout.findViewById(R.id.edtPhone);

        dialog.setView(register_layout);

        // Ponemos el botón
        dialog.setPositiveButton("REGISTRARSE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                // Comprobar validación
                if (TextUtils.isEmpty(edtEmail.getText().toString())){
                    Snackbar.make(rootLayout,"Por favor, introduzca su email",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                if (TextUtils.isEmpty(edtPassword.getText().toString())){
                    Snackbar.make(rootLayout,"Por favor, introduzca su contraseña",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                if (edtPassword.getText().toString().length() < 6){
                    Snackbar.make(rootLayout,"La contraseña no puede contener menos de 6 " +
                                    "caracteres", Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                if (TextUtils.isEmpty(edtPhone.getText().toString())){
                    Snackbar.make(rootLayout,"Por favor, introduzca su teléfono",
                            Snackbar.LENGTH_SHORT)
                            .show();
                    return;
                }

                // Registrar a un nuevo usuario
                auth.createUserWithEmailAndPassword(edtEmail.getText().toString(),
                        edtPassword.getText().toString())
                     .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                         @Override
                         public void onSuccess(AuthResult authResult) {
                             // Guardar usuario en db
                             User user = new User();
                             user.setEmail(edtEmail.getText().toString());
                             user.setPassword(edtPassword.getText().toString());
                             user.setName(edtName.getText().toString());
                             user.setPhone(edtPhone.getText().toString());

                             // Usamos el UID como key
                             users.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                     .setValue(user)
                                     .addOnSuccessListener(new OnSuccessListener<Void>() {
                                         @Override
                                         public void onSuccess(Void aVoid) {

                                             Snackbar.make(rootLayout,"Se ha registrado con éxito",
                                                     Snackbar.LENGTH_SHORT)
                                                     .show();

                                         }
                                     })
                                     .addOnFailureListener(new OnFailureListener() {
                                         @Override
                                         public void onFailure(@NonNull Exception e) {
                                             Snackbar.make(rootLayout,"Ha ocurrido un fallo durante su registro "+ e.getMessage(),
                                                     Snackbar.LENGTH_SHORT)
                                                     .show();
                                         }
                                     });
                         }
                     })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Snackbar.make(rootLayout,"Ha ocurrido un fallo durante su registro "+ e.getMessage(),
                                        Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                        });

            }
        });

        dialog.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        
        dialog.show();


    }
}
