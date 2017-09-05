package com.project.hamrorestaurant;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sugam on 8/21/2017.
 */

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {

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

        Restaurant currentRestaurant = getItem(position);


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

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.restaurantImage);
        String imageUrl = currentRestaurant.getImageResource();
        if (imageUrl != null) {
            // Using Picasso, download the Image from the internet. Picasso will automatically handle
            // view recycling and will not re-download the image if it has already been downloaded.
            Picasso.with(getContext()).load(imageUrl).into(imageView);
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }


        return listItemView;
    }

}
