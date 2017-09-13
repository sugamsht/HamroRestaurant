package com.project.hamrorestaurant;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.design.widget.TabLayout;


import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private Restaurant currentRestaurant;

    public Double getLatitude() {
        Double latitude = currentRestaurant.getLatitude();
        return latitude;
    }

    public Double getLongitude() {
        Double longitude = currentRestaurant.getLongitude();
        return longitude;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get the intent and retrieve the Restaurant object from it.
        Intent intent = getIntent();
        currentRestaurant = intent.getParcelableExtra("currentRestaurant");

        // Get references to the views in the layout
        ImageView detailImageView = (ImageView) findViewById(R.id.detail_image_view);
        TextView titleTextView = (TextView) findViewById(R.id.detail_restaurant_title);


//        TextView restaurantTags=(TextView)findViewById(R.id.restaurant_tags);
//        restaurantTags.setText(currentRestaurant.getTags());
//
//
//        TextView restaurantStatusView=(TextView)findViewById(R.id.open_now);
//        restaurantStatusView.setText(currentRestaurant.getOpenNow());


//        Double latitude = currentRestaurant.getLatitude();
//        Double longitude= currentRestaurant.getLongitude();

        String imageUrl = currentRestaurant.getImageResource();
        if (imageUrl != null) {
            Picasso.with(this).load(imageUrl).into(detailImageView);
        } else {
            detailImageView.setVisibility(View.GONE);
        }

        titleTextView.setText(currentRestaurant.getRestaurantName());


        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);


        // Create an adapter that knows which fragment should be shown on each page
        ViewPagerFragmentAdapter adapter = new ViewPagerFragmentAdapter(this, getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);


        // Find the tab layout that shows the tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's adapter's titles
        //      by calling onPageTitle()
        tabLayout.setupWithViewPager(viewPager);
    }
}