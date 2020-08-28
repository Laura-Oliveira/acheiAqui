package com.acheiAqui.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.acheiAqui.Control.RegisterFoodListActivity;

import acheiaqui.com.acheiAqui.R;

public class ConfirmShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_shop);

    }

    public void onClickButton(View view)
    {
        Intent intent = new Intent(this, RegisterFoodListActivity.class);
        startActivity(intent);
    }
}