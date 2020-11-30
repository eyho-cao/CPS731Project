package com.example.cps731project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPage extends AppCompatActivity {

    Button findRestaurants;
    Button settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        findRestaurants = findViewById(R.id.findRestaurantsBtn);
        settings = findViewById(R.id.SettingsBtn);
        findRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LaunchFindRestaurants();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LaunchSettings();
            }
        });
    }

    void LaunchFindRestaurants()
    {
        Intent intent = new Intent(this, FindRestaurantsPage.class);
        startActivity(intent);
    }

    void LaunchSettings()
    {
        Intent intent = new Intent(this, SettingsPage.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}