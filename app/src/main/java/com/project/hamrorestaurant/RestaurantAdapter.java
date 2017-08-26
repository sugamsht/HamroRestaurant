package com.project.hamrorestaurant;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sugam on 8/21/2017.
 */

public class RestaurantAdapter extends ArrayAdapter {

    /**
     * Resource ID for the background color for this list of words
     */
    private int mColorResourceId;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurants) {
        super(context, 0, restaurants);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.restaurant_list_item, parent, false);
        }

        Restaurant currentRestaurant = (Restaurant) getItem(position);

        ImageView imageView =(ImageView) listItemView.findViewById(R.id.restaurantImage);
        imageView.setImageResource(currentRestaurant.getImageResourceId());

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.restaurantName);
        nameTextView.setText(currentRestaurant.getRestaurantName());

        TextView locationTextView = (TextView) listItemView.findViewById(R.id.restaurantLocation);
        locationTextView.setText(currentRestaurant.getRestaurantLocation());

        TextView distanceTextView = (TextView) listItemView.findViewById(R.id.distance);
        distanceTextView.setText(String.valueOf(currentRestaurant.getDistance()));

//        // Set the theme color for the list item
//        View textContainer = listItemView.findViewById(R.id.text_container);
//        // Find the color that the resource ID maps to
//        int color = ContextCompat.getColor(getContext(), mColorResourceId);
//        // Set the background color of the text container View
//        textContainer.setBackgroundColor(color);



        return listItemView;
    }
}
