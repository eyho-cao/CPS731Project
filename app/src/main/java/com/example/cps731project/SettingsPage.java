package com.example.cps731project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;


public class SettingsPage extends AppCompatActivity {

    private String sharedPrefsFile = "com.example.cps731project";
    SharedPreferences userSettings;
    SharedPreferences.Editor userSettingsEditor;
    String userTheme;
    boolean userAudio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);

        userSettings = getSharedPreferences(sharedPrefsFile, MODE_PRIVATE);
        userSettingsEditor = userSettings.edit();
        userTheme = userSettings.getString("userTheme", "light");
        userAudio = userSettings.getBoolean("userAudio", true);

        RadioGroup themeSelect = findViewById(R.id.themeRdoGrp);
        themeSelect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //userSettingsEditor.putString("userTheme");
                if(i == R.id.selectLightTheme)
                {
                    userSettingsEditor.putString("userTheme", "light");
                }
                else if(i == R.id.selectDarkTheme)
                {
                    userSettingsEditor.putString("userTheme","dark");
                }
                userSettingsEditor.apply();
            }
        });
        RadioButton lightThemeBtn = findViewById(R.id.selectLightTheme);
        RadioButton darkThemeBtn = findViewById(R.id.selectDarkTheme);
        Switch audioSelect = findViewById(R.id.soundToggle);

        audioSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                userSettingsEditor.putBoolean("userAudio", b);
                userSettingsEditor.apply();
            }
        });
        if(userTheme.equals("light"))
        {
            lightThemeBtn.setChecked(true);
            darkThemeBtn.setChecked(false);
        }
        else if(userTheme.equals("dark"))
        {
            lightThemeBtn.setChecked(false);
            darkThemeBtn.setChecked(true);
        }
        if(userAudio)
        {
            audioSelect.setChecked(true);
        }
        else
        {
            audioSelect.setChecked(false);
        }
    }


}