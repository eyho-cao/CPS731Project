package com.example.cps731project;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {
    private static final String TAG = "Login Page";

    String theme;
    boolean audio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        TextView view = findViewById(R.id.textView);
        if(getIntent().hasExtra("userTheme") && getIntent().hasExtra("userAudio")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            theme = getIntent().getStringExtra("userTheme");
            audio = getIntent().getBooleanExtra("userAudio", true);
        }
        view.setText("theme: " +theme +"\naudio: " +audio);
    }
}