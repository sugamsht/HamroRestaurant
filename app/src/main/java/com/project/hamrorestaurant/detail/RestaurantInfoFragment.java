package com.project.hamrorestaurant.detail;


import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.hamrorestaurant.QueryDetails;
import com.project.hamrorestaurant.QueryUtils;
import com.project.hamrorestaurant.R;
import com.project.hamrorestaurant.Restaurant;
import com.project.hamrorestaurant.RestaurantAdapter;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantInfoFragment extends Fragment {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final String GOOGLE_PLACE_ID_URL =
            "https://maps.googleapis.com/maps/api/place/details/json?";
    final String KEY_PARAM = "key";
    final String GOOGLE_PLACE_ID = "placeid";
    MapView mMapView;
    private GoogleMap googleMap;
    private Restaurant currentRestaurant;
    private String placeidurl;
    //Adapter for the list of restaurants
    private RestaurantAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_restaurant_info, container, false);


//        TextView restaurantStatusView=(TextView)rootView.findViewById(R.id.open_now);
//        restaurantStatusView.setText(currentRestaurant.getOpenNow());
//
//        TextView restaurantTags=(TextView)rootView.findViewById(R.id.restaurant_tags);
//        restaurantTags.setText(currentRestaurant.getTags());

        final String place_id = ((DetailActivity) getActivity()).getRestaurantPlaceId();


        //add queries
        Uri mDestinationUri = Uri.parse(GOOGLE_PLACE_ID_URL).buildUpon()
                .appendQueryParameter(GOOGLE_PLACE_ID, place_id.toString())
//                .appendQueryParameter(GOOGLE_PLACE_ID, place_id.getText().toString())
                .appendQueryParameter(KEY_PARAM, "AIzaSyBDs67EpHkxvp3y3HETDsYrQXfe35hZ6hU")
                .build();

        placeidurl = mDestinationUri.toString();


        // Start the AsyncTask to fetch the restaurant data

        RestaurantAsyncTask task = new RestaurantAsyncTask();
        task.execute(placeidurl);
        Log.v(LOG_TAG, "The placeIdURL is " + placeidurl);

//        TextView testTextView = (TextView) getView().findViewById(R.id.testid);
//        final String testid=((DetailActivity) getActivity()).getAuthorName();
//        testTextView.setText(testid);


        final Double latitude = ((DetailActivity) getActivity()).getLatitude();
        final Double longitude = ((DetailActivity) getActivity()).getLongitude();


        mMapView = rootView.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately


        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;


//                googleMap.setMyLocationEnabled(true);
                // For showing a move to my location button

                googleMap.getUiSettings().setMyLocationButtonEnabled(true);

                // For dropping a marker at a point on the Map

                LatLng thimi = new LatLng(latitude, longitude);
//                Log.v(LOG_TAG,"The object latitude and longitude is:"+latitude +longitude);


                googleMap.addMarker(new MarkerOptions().position(thimi).title("Marker Title").snippet("Restaurant"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(thimi).zoom(15).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    private class RestaurantAsyncTask extends AsyncTask<String, Void, List<RestaurantDetail>> {
        @Override
        protected List<RestaurantDetail> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<RestaurantDetail> result = QueryDetails.fetchRestaurantData(urls[0]);
            return result;
        }


    }
}

