package com.example.cps731project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterPage extends AppCompatActivity {

    private static final String TAG = "Register Page";
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    EditText user;
    EditText pass;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        user = findViewById(R.id.UsernameET);
        pass = findViewById(R.id.PasswordET);
        registerBtn = findViewById(R.id.RegisterBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = user.getText().toString();
                String password = pass.getText().toString();
                CheckDetails(username, password);
            }
        });
    }

    //Get number of documents to compare to
    void CheckDetails(final String username, final String password)
    {
        if(!username.equals("") && !password.equals("")) {
            db.collection("userAccounts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    int count = 0;
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            count++;
                            Log.d("TAG", "count: " + count);
                        }
                        CreateAccount(username, password, count);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }

                }
            });
        }
        else
        {
            Toast toast = Toast.makeText(this, "Username and Password cannot be blank", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //check that username is not taken
    void CreateAccount(String username, String password, int count)
    {
        CollectionReference accounts = db.collection("userAccounts");
        Map<String, Object> newAccount = new HashMap<>();
        newAccount.put("password", password);
        newAccount.put("username", username);
        accounts.document(Integer.toString(count+1)).set(newAccount);
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}