package com.example.cps731project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer sing;
    private String sharedPrefsFile = "com.example.cps731project";
    String userTheme;
    boolean userAudio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Timer timer = new Timer();
        SharedPreferences userSettings = getSharedPreferences(sharedPrefsFile, MODE_PRIVATE);
        userTheme = userSettings.getString("userTheme", "light");
        userAudio = userSettings.getBoolean("userAudio", true);
        TextView view = findViewById(R.id.textview);
        view.setText("theme: " +userTheme +"\naudio: " +userAudio);
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
        timer.schedule(task, 5000);
    }

    private void launchLoginPage()
    {
        Intent intent = new Intent(this, SettingsPage.class);
        intent.putExtra("userTheme", userTheme);
        intent.putExtra("userAudio", userAudio);
        startActivity(intent);
    }
}