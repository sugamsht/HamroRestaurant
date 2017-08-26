package com.project.hamrorestaurant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create a list of words
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        restaurants.add(new Restaurant(R.drawable.bharyang, "Bharyang", "Gatthaghar-15,Thimi",0.2));
        restaurants.add(new Restaurant(R.drawable.bharyang, "Hello", "Chinaroad,Thimi",5.1));
        restaurants.add(new Restaurant(R.drawable.bharyang, "How", "Gatthaghar-15,Thimi",8.0));
        restaurants.add(new Restaurant(R.drawable.bharyang, "Are", "Gatthaghar-15,Thimi",6.2));
        restaurants.add(new Restaurant(R.drawable.bharyang, "You", "Gatthaghar-15,Thimi",2.3));
        restaurants.add(new Restaurant(R.drawable.bharyang, "My", "Gatthaghar-15,Thimi",1.2));
        restaurants.add(new Restaurant(R.drawable.bharyang, "Friend", "Gatthaghar-15,Thimi",2.2));

        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(this, restaurants);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(restaurantAdapter);

    }
}
