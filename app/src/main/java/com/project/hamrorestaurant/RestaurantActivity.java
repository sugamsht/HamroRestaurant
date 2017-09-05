package com.project.hamrorestaurant;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends AppCompatActivity {

    private static final String LOG_TAG = RestaurantActivity.class.getName();

    /**
     * URL for earthquake data from the USGS dataset
     */
    private static final String GOOGLE_REQUEST_URL =
            "https://maps.googleapis.com/maps/api/place/search/json?location=27.6746731,85.3767524&radius=500&types=restaurant&sensor=false&key=AIzaSyBDs67EpHkxvp3y3HETDsYrQXfe35hZ6hU";
    //Adapter for the list of restaurants
    private RestaurantAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Find a reference to the {@link ListView} in the layout
        ListView restaurantListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of restaurants as input
        mAdapter = new RestaurantAdapter(this, new ArrayList<Restaurant>());

        restaurantListView.setAdapter(mAdapter);

        // Start the AsyncTask to fetch the restaurant data
        RestaurantAsyncTask task = new RestaurantAsyncTask();
        task.execute(GOOGLE_REQUEST_URL);

        // Set up a clickListener on the listView to open up the DetailActivity if the user clicks
        // on a list item.

        restaurantListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Find the current book that was clicked on
                Restaurant currentRestaurant = mAdapter.getItem(position);

                // Create an intent to the DetailActivity, and send the currentBook to the Activity
                // as well.
                Intent intent = new Intent(RestaurantActivity.this, DetailActivity.class);
                intent.putExtra("currentRestaurant", currentRestaurant);
                startActivity(intent);
            }
        });


    }


    private class RestaurantAsyncTask extends AsyncTask<String, Void, List<Restaurant>> {
        @Override
        protected List<Restaurant> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Restaurant> result = QueryUtils.fetchRestaurantData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Restaurant> data) {
            // Clear the adapter of previous restaurant data
            mAdapter.clear();
            // If there is a valid list of {@link Resturant}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}
