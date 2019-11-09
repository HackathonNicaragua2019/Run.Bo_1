package com.crafters.rumbos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aditya.bustrack.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.nio.channels.InterruptedByTimeoutException;
import java.sql.DriverManager;

import butterknife.BindView;
import butterknife.ButterKnife;


public class activity_login extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private AnimationDrawable animationDrawable;

    @BindView(R.id.text_input_layout_email)
    TextInputLayout emailWrapper;
    @BindView(R.id.text_input_layout_password)
    TextInputLayout passwordWrapper;
    @BindView(R.id.btnLogin)
    Button login;

    @BindView(R.id.registerbtn)
    Button registerbtn;
    @BindView(R.id.chooser_spinner)
    Spinner userType;

    private TextInputEditText emailInput;
    private TextInputEditText passwordInput;
    public static final String LOG_TAG = activity_login.class.getSimpleName();
    private int bus_num = 0;

    //Firebase Utils
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private ProgressDialog mProgress;

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onCreate(Bundle savedIntanceState) {
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        if (nInfo != null && nInfo.isConnected()) {

        } else {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(activity_login.this);
            a_builder.setMessage("Por Favor Habilita la Conexion a Internet!!!")
                    .setCancelable(false)
                    .setPositiveButton("Configuracion", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent in = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                            startActivity(in);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alert = a_builder.create();
            alert.setTitle("No Conexion a Internet");
            alert.show();
            {
            }

        }
        /*
        // coordinador de inicio
        coordinatorLayout = (CoordinatorLayout) findViewById (R.id.activity_login);
        // inicializando la animación dibujable obteniendo fondo del diseño de restricción
        animationDrawable = (AnimationDrawable) coordinatorLayout.getBackground ();

        // configuración ingresa la duración de la animación de desvanecimiento a 5 segundos
        animationDrawable.setEnterFadeDuration (5000);

        // establecer la duración de la animación de desvanecimiento de salida en 2 segundos
        animationDrawable.setExitFadeDuration (2000);
*/

        mProgress = new ProgressDialog(activity_login.this, R.style.AppTheme_Dark_Dialog);
        mProgress.setMessage("Verificando...");
        mProgress.setCancelable(false);
        mProgress.setIndeterminate(true);

        // overridePendingTransition(R.anim.slide_in_right, R.anim.stay_in_place);
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Boolean ut = getPreferences(Context.MODE_PRIVATE).getBoolean(getString(R.string.isDriver), false);
                    if (ut){
                        //Intent intent = new Intent(activity_login.this, DriverMapsActivity.class);
                        //startActivity(intent);
                        finish();
                    }else{
                        //Intent intent = new Intent(activity_login.this, StudentMapasActivity.class);
                        //startActivity(intent);
                        //finish();
                    }
                }
            }
        };

//        switchToSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, SignUPActivity.class);
//                startActivity(intent);
//            }
//        });


    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mProgress.show();
            hideKeyBoard();
            emailInput = (TextInputEditText) emailWrapper.getEditText();
            passwordInput = (TextInputEditText) passwordWrapper.getEditText();
            String email = emailInput.getText().toString();
            String password = passwordInput.getText().toString();
            String user = userType.getSelectedItem().toString();

           /* Guardar detalles del usuario en un archivo de preferencias compartido, es decir, si es conductor o no
             y verificará desde ese pref compartido al iniciar sesión!*/

           if (userType.getSelectedItem().toString().equals("Conductor")){
               SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
               editor.putBoolean(getString(R.string.isDriver),true);
               editor.commit();
           }else{
               SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
               editor.remove(getString(R.string.isDriver));
               editor.commit();
           }

           mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity_login.this, new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if (!task.isSuccessful()){
                       Toast.makeText(activity_login.this, "Error Inicio Sesion", Toast.LENGTH_SHORT).show();
                   }
               }
           });

        }
    });

    registerbtn.setOnClickListener(new View.OnClickListener(){
    @Override
        public void onClick(View view){
        mProgress.show();
        hideKeyBoard();
        emailInput = (TextInputEditText) emailWrapper.getEditText();
        passwordInput = (TextInputEditText) passwordWrapper.getEditText();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        String user = userType.getSelectedItem().toString();

        if(user.equals("Conductor")){
            SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
            editor.putBoolean(getString(R.string.isDriver), true);
            editor.commit();
        } else {
            SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
            editor.remove(getString(R.string.isDriver));
            editor.commit();
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity_login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {

                    Toast.makeText(activity_login.this, "\n" + "¡Error de registro!", Toast.LENGTH_SHORT).show();
                } else {
                    String user_id = mAuth.getCurrentUser().getUid();

                    Log.i(LOG_TAG, "Usuario es : " + user);
                    Boolean isDriver = getPreferences(Context.MODE_PRIVATE).getBoolean(getString(R.string.isDriver), false);
                    if (isDriver) {
                        DatabaseReference user_db = FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Conductor").child(user_id);
                        user_db.setValue(true);
                    } else {
                        DatabaseReference user_db = FirebaseDatabase.getInstance().getReference().child("Usuarios").child("Pasajeros").child(user_id);
                        user_db.setValue(true);
                    }
                }
            }
        });
    }
    });

    }

    @Override
    public void finish() {
        super.finish();
        //LoginActivity.this.overridePendingTransition(0, R.anim.slide_out_right);
    }

    private void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            view.clearFocus();
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning()) {
            // start the animation
            animationDrawable.start();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning()) {
            // stop the animation
            animationDrawable.stop();
        }
    }
}




