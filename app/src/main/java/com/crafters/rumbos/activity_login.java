package com.crafters.rumbos;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.aditya.bustrack.R;

import org.w3c.dom.Text;

import butterknife.BindView;


public class activity_login extends AppCompatActivity {

    private CoordinatorLayout coordinatorLayout;
    private AnimationDrawable animationDrawable;
    TextInputLayout email;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        email=(TextInputLayout) findViewById(R.id.text_input_layout_email);
    }
}

