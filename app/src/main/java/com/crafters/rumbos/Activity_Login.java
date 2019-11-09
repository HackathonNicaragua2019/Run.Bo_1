package com.crafters.rumbos;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;

public class Activity_Login extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private AnimationDrawable animationDrawable;

    @BindView(com.example.aditya.bustrack.R.id.text_input_layout_email)
    TextInputLayout emailWrapper;
    @BindView(com.example.aditya.bustrack.R.id.text_input_layout_password)
    TextInputLayout passwordWrapper;
    @BindView(com.example.aditya.bustrack.R.id.btnLogin)
    Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        emailWrapper=(TextInputLayout)findViewById(R.id.)
    }
}