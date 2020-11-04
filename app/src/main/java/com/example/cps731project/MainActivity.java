package com.example.cps731project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer sing;
    SharedPreferences userSettings = getSharedPreferences("MySharedPref", MODE_PRIVATE);
    String userTheme = userSettings.getString("userTheme", "light");
    boolean userAudio = userSettings.getBoolean("userAudio", true);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sing= MediaPlayer.create(this,R.raw.ping);
        if(userAudio)
        {
            sing.start();
        }
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                if(userAudio)
                {
                    sing.stop();
                }
                launchLoginPage();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 8000);
    }

    private void launchLoginPage()
    {
        Intent intent = new Intent(this, LoginPage.class);
        intent.putExtra("userTheme", userTheme);
        intent.putExtra("userAudio", userAudio);
        startActivity(intent);
    }
}