package com.crafters.rumbos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.aditya.bustrack.R;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

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
    protected void onStop(){
        super.onStop();
        mAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        ConnectivityManager cm =(ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        if (nInfo !=null && nInfo.isConnected()){

        }
        else{
            AlertDialog.Builder a_builder = new AlertDialog.Builder(activity_login.this);
            a_builder.setMessage("Please enable internet connection !!!")
                    .setCancelable(false)
                    .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Intent in = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                            startActivity(in);

                        }
                    })
                    .setNegativeButton("Cancel", new  DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i){
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alert = a_builder.create();
                    alert.setTitle("No Conexion a Internet");
                    alert.show();{}

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

    }

    }



