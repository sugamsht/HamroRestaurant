package com.project.hamrorestaurant;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.project.hamrorestaurant.data.RestaurantDBHelper;
import com.project.hamrorestaurant.data.RestaurantEntry;
import com.project.hamrorestaurant.detail.DetailActivity;
import com.project.hamrorestaurant.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class RestaurantActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String LOG_TAG = RestaurantActivity.class.getName();
    private static final int Restaurant_LOADER_ID = 1;
    /**
     * URL for restaurant data from the Google Places API
     */
    private static final String GOOGLE_REQUEST_URL =
            "https://maps.googleapis.com/maps/api/place/search/json?";
    //Google Place API Parameters
    final String LOCATION_PARAM = "location";

    //    private static final String GOOGLE_PLACE_ID_URL=
//            "https://maps.googleapis.com/maps/api/place/details/json?";
    final String RADIUS_PARAM = "radius";
    final String KEYWORD_PARAM = "keyword";
    //  final String LANGUAGE_PARAM = "language";
    final String KEY_PARAM = "key";
    final String TYPES = "types";
    final String KEYWORD = "keyword";
    RestaurantDBHelper mDbHelper;
    /**
     * Variables to check network status and to get Loader Manager
     */
    private ConnectivityManager connMgr;
    private NetworkInfo networkInfo;
    private String realURL;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private Double latitude = 27.6737566;
    private Double longitude = 85.3807433;


    private LocationManager locationManager;
    private LocationListener listener;


    //Adapter for the list of restaurants
    private RestaurantAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mToolbar = findViewById(R.id.toolbar);
        mNavigationView.setNavigationItemSelectedListener(this);

        mToolbar.setTitle(R.string.app_name);

        /*This is used to set the toggle button for navigation drawer*/
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        final EditText searchInput = findViewById(R.id.inputSearch);
        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Parse the base URL and prepare it for query parameters to be added.
                Uri mDestinationUri = Uri.parse(GOOGLE_REQUEST_URL).buildUpon()
                        .appendQueryParameter(LOCATION_PARAM, latitude + "," + longitude)
                        .appendQueryParameter(RADIUS_PARAM, "1000")
                        .appendQueryParameter(TYPES, "restaurant")
                        .appendQueryParameter(KEYWORD, searchInput.getText().toString().trim())
                        .appendQueryParameter(KEY_PARAM, "AIzaSyBDs67EpHkxvp3y3HETDsYrQXfe35hZ6hU")
                        .build();

                realURL = mDestinationUri.toString();
                // Start the AsyncTask to fetch the restaurant data
                RestaurantAsyncTask task = new RestaurantAsyncTask();
                task.execute(realURL);
                Log.v(LOG_TAG, "The updatedrealURL is " + realURL);

                return true;

            }
        });


        // Parse the base URL and prepare it for query parameters to be added.
        Uri mDestinationUri = Uri.parse(GOOGLE_REQUEST_URL).buildUpon()
                .appendQueryParameter(LOCATION_PARAM, latitude + "," + longitude)
                .appendQueryParameter(RADIUS_PARAM, "2000")
                .appendQueryParameter(TYPES, "restaurant")
                .appendQueryParameter(KEYWORD, searchInput.getText().toString().trim())
                .appendQueryParameter(KEY_PARAM, "AIzaSyBDs67EpHkxvp3y3HETDsYrQXfe35hZ6hU")
                .build();


        realURL = mDestinationUri.toString();

        // Start the AsyncTask to fetch the restaurant data
        RestaurantAsyncTask task = new RestaurantAsyncTask();
        task.execute(realURL);
        Log.v(LOG_TAG, "The realURL is " + realURL);


        // Find a reference to the {@link ListView} in the layout
        ListView restaurantListView = findViewById(R.id.list);

        // Create a new adapter that takes an empty list of restaurants as input
        mAdapter = new RestaurantAdapter(this, new ArrayList<Restaurant>());

        restaurantListView.setAdapter(mAdapter);


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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_insert_dummy_data) {
            return true;
        }

        if (id == R.id.action_delete_all_entries) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the home action
            Intent home = new Intent(RestaurantActivity.this, RestaurantActivity.class);
            startActivity(home);
        } else if (id == R.id.nav_signin) {
            Intent signIn = new Intent(RestaurantActivity.this, LoginActivity.class);
            startActivity(signIn);

        } else if (id == R.id.nav_favorite) {
            insertReview();


        } else if (id == R.id.nav_review) {

        } else if (id == R.id.nav_suggest) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_about) {
            Intent contact = new Intent(RestaurantActivity.this, ContactActivity.class);
            startActivity(contact);

        } else if (id == R.id.nav_setting) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void insertReview() {
        mDbHelper = new RestaurantDBHelper(this);
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RestaurantEntry.COLUMN_REVIEW_NAME, "Sugam Shrestha");
        values.put(RestaurantEntry.COLUMN_REVIEW_COMMENT, "THe food is really good my friend " +
                "like really really gooood");
        values.put(RestaurantEntry.COLUMN_REVIEW_RATING, 5);

        long newRowId = db.insert(RestaurantEntry.TABLE_REVIEW, null, values);
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
