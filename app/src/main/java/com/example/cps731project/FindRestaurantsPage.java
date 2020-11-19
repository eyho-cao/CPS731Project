package com.example.cps731project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Objects;

public class FindRestaurantsPage extends AppCompatActivity {

    PlacesClient placesClient;
    ApplicationInfo app;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_restaurants_page);
        try
        {
            app = this.getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        bundle = app.metaData;
        // Initialize the SDK
        Places.initialize(getApplicationContext(), Objects.requireNonNull(bundle.getString("com.google.android.geo.API_KEY")));

        // Create a new PlacesClient instance
        placesClient = Places.createClient(this);
    }
}