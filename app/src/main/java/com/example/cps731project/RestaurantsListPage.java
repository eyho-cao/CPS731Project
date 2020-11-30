package com.example.cps731project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class RestaurantsListPage extends AppCompatActivity {

    ArrayList<String> restaurants;
    ArrayList<Double> ratings;
    ArrayList<String> address;
    private RecyclerView mRecyclerView;
    private RestaurantListAdapter mAdapter;
    Button chooseRestaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurants_list_page);

        restaurants = (ArrayList<String>) getIntent().getSerializableExtra("restaurants");
        ratings = (ArrayList<Double>) getIntent().getSerializableExtra("ratings");
        address = (ArrayList<String>) getIntent().getSerializableExtra("address");

        chooseRestaurant = findViewById(R.id.rndBtn);

        mRecyclerView = findViewById(R.id.recyclerView);
        mAdapter = new RestaurantListAdapter(this, restaurants, address, ratings);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        chooseRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseRandom();
            }
        });
    }

    void ChooseRandom()
    {
        int random = (int) (restaurants.size() * Math.random());
        String chosen = restaurants.get(random);
        Toast toast = Toast.makeText(this, "Restaurant Chosen: " +chosen, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, FindRestaurantsPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}