package com.example.cps731project;

import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class UnitTests {
    FindRestaurantsPage frPage;
    protected LocationManager locationManager;
    protected LocationListener listener;
    String latitude="";
    String longitude="";
    MainActivity mainAct;
    boolean userAudio;
    LoginPage lgnPage;
    @Before
    public void SetUp()
    {
        frPage = new FindRestaurantsPage();
        lgnPage = new LoginPage();
        mainAct = new MainActivity();
    }

    @Test
    public void restaurants_areChosenWCriteria()
    {
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=43.65822542336006,%20-79.38159876389952&radius=2000&type=restaurant&keyword=Chinese&minprice=0&opennow=false&key=AIzaSyAdgpn9vQLiNCgHd2MpDujMuVXg0mTZ0mY";
        final ArrayList<String> restaurants = new ArrayList<String>(Arrays.asList("Hong Shing Chinese Restaurant","Swatow Restaurant","Lee Chen Asian Bistro","Tasty Chinese Food","Bamboo Buddha Chinese Restaurant","House Of Gourmet","Sky Dragon Chinese Restaurant 龍翔酒樓","Lee Chen Asian Bistro","China King","Mother's Dumplings","Asian Legend","Harmony Restaurant","King's Noodle Restaurant","Dynasty","Crown Princess Fine Dining","Lai Wah Heen","China Gourmet Takeout","Noodle King","98aroma"));
        frPage.TestFindRestaurants(url);
        Timer timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                assertArrayEquals(restaurants.toArray(), frPage.restaurants.toArray());
            }
        };
        timer.schedule(task, 2000);
    }

    @Test
    public void location_isSet()
    {
        locationManager = (LocationManager) frPage.getSystemService();
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = Double.toString(location.getLatitude());
                longitude = Double.toString(location.getLongitude());
            }
        };
        assertEquals(latitude, frPage.latitude);
        assertEquals(longitude, frPage.longitude);
    }

    @Test
    public void randomRestaurant_isChosen()
    {
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=43.65822542336006,%20-79.38159876389952&radius=2000&type=restaurant&keyword=Chinese&minprice=0&opennow=false&key=AIzaSyAdgpn9vQLiNCgHd2MpDujMuVXg0mTZ0mY";
        final ArrayList<String> restaurants = new ArrayList<String>(Arrays.asList("Hong Shing Chinese Restaurant","Swatow Restaurant","Lee Chen Asian Bistro","Tasty Chinese Food","Bamboo Buddha Chinese Restaurant","House Of Gourmet","Sky Dragon Chinese Restaurant 龍翔酒樓","Lee Chen Asian Bistro","China King","Mother's Dumplings","Asian Legend","Harmony Restaurant","King's Noodle Restaurant","Dynasty","Crown Princess Fine Dining","Lai Wah Heen","China Gourmet Takeout","Noodle King","98aroma"));
        frPage.TestFindRestaurants(url);
        Timer timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                int index = (int )Math.random()*10;
                assertEquals(restaurants.get(index), frPage.restaurants.get(index));
            }
        };
        timer.schedule(task, 2000);
    }

    //not complete
    @Test
    public void sharedPreferences_areCorrect()
    {
        String sharedPrefsFile = "com.example.cps731project";
        SharedPreferences userSettings = mainAct.getShadPref();
        userAudio = false;
        assertEquals(userAudio, mainAct.userAudio);
    }

    @Test
    public void restaurants_areChosen()
    {
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=43.65822542336006,%20-79.38159876389952&radius=2000&type=restaurant&minprice=0&key=AIzaSyAdgpn9vQLiNCgHd2MpDujMuVXg0mTZ0mY";
        final ArrayList<String> restaurants = new ArrayList<>(Arrays.asList("Oliver & Bonacini Café Grill", "Yonge & Front","The One Eighty","7 West Cafe","Adega Restaurante","Terroni","The Keg Steakhouse + Bar - York Street","The Senator","Jump Restaurant","Donatello Restaurant","Salad King","Canoe","IL FORNELLO on King","Jack Astor's Bar & Grill","Little India Restaurant","Scotland Yard Pub","VOLOS","Mercatto","PLANTA Queen","KINKA IZAKAYA ORIGINAL","Lai Wah Heen"));
        frPage.TestFindRestaurants(url);
        Timer timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                int index = (int )Math.random()*10;
                assertEquals(restaurants.get(index), frPage.restaurants.get(index));
            }
        };
        timer.schedule(task, 2000);
    }
}
