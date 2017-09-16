package com.project.hamrorestaurant;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.Query;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantInfoFragment extends Fragment {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    MapView mMapView;
    private GoogleMap googleMap;

    private Restaurant currentRestaurant;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_restaurant_info, container, false);


//        TextView restaurantStatusView=(TextView)rootView.findViewById(R.id.open_now);
//        restaurantStatusView.setText(currentRestaurant.getOpenNow());
//
//        TextView restaurantTags=(TextView)rootView.findViewById(R.id.restaurant_tags);
//        restaurantTags.setText(currentRestaurant.getTags());


        final Double latitude = ((DetailActivity) getActivity()).getLatitude();
        final Double longitude = ((DetailActivity) getActivity()).getLongitude();




        mMapView = (MapView) rootView.findViewById(R.id.map);
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
}

