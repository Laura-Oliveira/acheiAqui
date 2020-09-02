package com.acheiAqui.Control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import acheiaqui.com.acheiAqui.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void registerNewShop(View view){

        Intent intent = new Intent(this, RegisterInfoShopActivity.class);
        startActivity(intent);
    }
}