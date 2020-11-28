package com.example.cps731project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class LoginPage extends AppCompatActivity {

    private static final String TAG = "Login Page";
    public FirebaseFirestore loginDB;
    TextView registerTxt;
    TextView loginResult;
    Button loginBtn;
    String theme;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        FirebaseApp.initializeApp(this);
        loginDB= FirebaseFirestore.getInstance();

        registerTxt = findViewById(R.id.RegisterTxtV);
        loginBtn = findViewById(R.id.RegisterBtn);
        loginResult = findViewById(R.id.loginResult);
        if(getIntent().hasExtra("userTheme")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            theme = getIntent().getStringExtra("userTheme");
        }
        registerTxt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                registerTxt.setTextColor(Color.parseColor("#1C63DD"));
                registerTxt.setText(R.string.registerTxtUL);
                return false;
            }
        });
        registerTxt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    registerTxt.setTextColor(Color.parseColor("#808080"));
                    registerTxt.setText(R.string.registerTxt);
                    OpenRegisterPage();
                }
                return false;
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText username = findViewById(R.id.UsernameET);
                EditText password = findViewById(R.id.PasswordET);
                CheckDetails(username.getText().toString(), password.getText().toString());
            }
        });
    }


    void OpenRegisterPage()
    {
        Intent intent = new Intent(this, RegisterPage.class);
        startActivity(intent);
    }
    void OpenMainPage()
    {
        Intent intent = new Intent(this, MainPage.class);
        startActivity(intent);
    }

    void CheckDetails(final String username, final String password)
    {
        loginDB.collection("userAccounts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                int count = 0;
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        count++;
                        Log.d("TAG", "count: " +count);
                    }
                    CompareDetails(username, password, count);
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }

            }
        });
    }

    void CompareDetails(final String username, final String password, int count)
    {
        DocumentReference docRef;
        for(int j =1; j <= count; j++)
        {
            docRef = loginDB.collection("userAccounts").document(Integer.toString(j));
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            Map<String, Object> map = document.getData();
                            String user = "";
                            String pass = "";
                            for (Map.Entry<String, Object> entry : map.entrySet()) {
                                if (entry.getKey().equals("username")) {
                                    user = entry.getValue().toString();
                                } else if (entry.getKey().equals("password")) {
                                    pass = entry.getValue().toString();
                                }
                            }
                            if (user.equals(username) && pass.equals(password)) {
                                //redirect to MainPage
                                OpenMainPage();
                            } else {
                                Log.d(TAG, "User/Password is wrong");
                                loginResult.setText("Username and/or Password is Incorrect");
                            }
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
        }
    }

}