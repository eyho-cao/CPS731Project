package com.example.cps731project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder>  {
    private final ArrayList<String> mRestaurantList;
    private final ArrayList<String> mAddressList;
    private final ArrayList<Double> mRatingsList;
    private LayoutInflater mInflater;

    public RestaurantListAdapter(Context context, ArrayList<String> restaurantList, ArrayList<String> addressList, ArrayList<Double> ratingsList) {

        mInflater = LayoutInflater.from(context);
        this.mRestaurantList = restaurantList;
        mAddressList = addressList;
        mRatingsList = ratingsList;
    }

    @NonNull
    @Override
    public RestaurantListAdapter.RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.restaurant_item,
                parent, false);
        return new RestaurantViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListAdapter.RestaurantViewHolder holder, int position) {
        String mCurrentName = mRestaurantList.get(position);
        String mCurrentDetails = "Rating: " +mRatingsList.get(position) +"\nAddress: " +mAddressList.get(position);
        holder.nameView.setText(mCurrentName);
        holder.detailsView.setText(mCurrentDetails);
    }

    @Override
    public int getItemCount() {
        return mRestaurantList.size();
    }


    private void getLayoutPosition() {
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView nameView;
        public final TextView detailsView;
        final RestaurantListAdapter mAdapter;
        public RestaurantViewHolder(View itemView, RestaurantListAdapter adapter) {
            super(itemView);
            nameView = itemView.findViewById(R.id.restaurantNameTxtV);
            detailsView = itemView.findViewById(R.id.restaurantDetailsTxtV);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in mWordList.
            String element = mRestaurantList.get(mPosition);
            // Notify the adapter, that the data has changed so it can
            // update the RecyclerView to display the data.
            mAdapter.notifyDataSetChanged();

        }
    }
}