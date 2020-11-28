package com.example.cps731project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class FindRestaurantsPage extends AppCompatActivity{

    protected LocationManager locationManager;
    protected LocationListener listener;
    protected String latitude, longitude;
    Context context;

    Spinner star;
    Spinner type;
    Spinner price;
    TextView txtV;
    PlacesClient placesClient;
    ApplicationInfo app;
    Bundle bundle;
    Button setLocation;
    Button findRestaurants;
    Switch open;
    Boolean locationFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_restaurants_page);
        context = this;

        txtV = findViewById(R.id.textView2);
        setLocation = findViewById(R.id.setLocationBtn);
        findRestaurants = findViewById(R.id.findRestaurantsBtn);
        star = findViewById(R.id.starsSpinner);
        type = findViewById(R.id.typeSpinner);
        price = findViewById(R.id.priceSpinner);
        open = findViewById(R.id.openSwitch);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = Double.toString(location.getLatitude());
                longitude = Double.toString(location.getLongitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                //
            }

            @Override
            public void onProviderEnabled(String s) {
                //
            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };
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

        setLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configure_button();
            }
        });
        findRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FindRestaurants();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                request_permission();
            }
        } else {
            // permission has been granted
            locationManager.requestLocationUpdates("gps", 5000, 0, listener);
        }
        locationFound = true;
    }

    private void request_permission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {

            Snackbar.make(findViewById(R.id.layout), "Location permission is needed because ...",
                   Snackbar.LENGTH_LONG)
                    .setAction("retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 10);
                        }
                    })
                    .show();
        } else {
            // permission has not been granted yet. Request it directly.
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 10);
        }
    }

    public void setLongitude(String lng)
    {
        longitude = lng;
    }

    public void setLatitude(String lat)
    {
        latitude = lat;
    }

    public void FindRestaurants()
    {
        if(locationFound) {
            //String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude +", " +longitude +"&radius=2000&type=restaurant";
            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=43.65822542336006, -79.38159876389952&radius=2000&type=restaurant"; //Younge-Dundas Square
            String restaurantType = type.getSelectedItem().toString();
            restaurants = new ArrayList<>();
            if (!restaurantType.equals("N/A")) {
                url += "&keyword=" + restaurantType;
            }
            String priceRange = price.getSelectedItem().toString();
            url += "&minprice=" + priceRange;
            Boolean openNow = open.isChecked();
            if (openNow) {
                url += "&opennow=true";
            }
            url += "&key=" + bundle.getString("com.google.android.geo.API_KEY");
            Log.d("QuerySent", url);
            new JsonTask().execute(url);
        }
        else
        {
            Toast toast = Toast.makeText(context, "Location has not been set. Please set location first!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void TestFindRestaurants(String url)
    {
        //Log.d("TestFindRestaurants Test", "URL: " +url);
        new JsonTask().execute(url);
    }

    public Object getSystemService() {
        return getSystemService(LOCATION_SERVICE);
    }

    private class JsonTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    //Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                ParseResults(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    JSONObject reader;
    ArrayList<Double> ratings= new ArrayList<>();
    ArrayList<String> restaurants = new ArrayList<>();
    ArrayList<String> addresses = new ArrayList<>();
    void ParseResults(String jsonString) throws JSONException {
        int minStar;
        if(star.getSelectedItem().toString() != "N/A")
        {
            minStar = 1;
        }
        else
        {
            minStar = Integer.parseInt(star.getSelectedItem().toString());
        }
        reader = new JSONObject(jsonString);
        JSONArray results = reader.getJSONArray("results");
        for(int i = 0; i < results.length(); i++)
        {
            JSONObject temp = results.getJSONObject(i);
            double tempRating = temp.getDouble("rating");
            if(tempRating >= minStar)
            {
                ratings.add(tempRating);
                restaurants.add(temp.getString("name"));
                addresses.add(temp.getString("vicinity"));
            }
        }
        LaunchRestaurantsList();
    }

    void LaunchRestaurantsList()
    {
        Toast toast = Toast.makeText(context, "Location: " +latitude +", " +longitude, Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent(this, RestaurantsListPage.class);
        intent.putExtra("restaurants", restaurants);
        intent.putExtra("ratings", ratings);
        intent.putExtra("address", addresses);
        startActivity(intent);
    }

    String outputRestaurants(ArrayList<String> a)
    {
        String out = "[";
        for(int i = 0; i < a.size(); i++)
        {
            if(i > 0)
            {
                out += ",";
            }
            out+= ""+a.get(i);
        }
        out+= "]";
        return out;
    }
}
