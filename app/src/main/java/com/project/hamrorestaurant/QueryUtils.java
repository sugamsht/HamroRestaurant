package com.project.hamrorestaurant;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sugam on 8/26/2017.
 */


public class QueryUtils {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return a list of {@link Restaurant} objects.
     */
    public static List<Restaurant> fetchRestaurantData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        // Extract relevant fields from the JSON response and create a list of {@link Restaurant}s
        List<Restaurant> restaurants = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Restaurants}s
        return restaurants;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the restaurant JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Restaurant} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Restaurant> extractFeatureFromJson(String restaurantJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(restaurantJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding restaurants to
        List<Restaurant> restaurants = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(restaurantJSON);

            // Extract the JSONArray associated with the key called "results",
            // which represents a list of features (or restaurants).
            JSONArray restaurantArray = baseJsonResponse.getJSONArray("results");

            // For each restaurant in the restaurantArray, create an {@link Restaurant} object
            for (int i = 0; i < restaurantArray.length(); i++) {

                String imageResource = null;
                String restaurantName = null;
                String restaurantLocation = null;
                String imageResourceA = null;
                String lati = null;
                String longi = null;
                Double latitude = null;
                Double longitude = null;
                Double rating = null;
                String restaurantStatus = "true";
                String restaurantTags = null;


                // Get a single restaurant at position i within the list of restaurants
                JSONObject currentRestaurant = restaurantArray.getJSONObject(i);

                JSONObject geometry = currentRestaurant.getJSONObject("geometry");

                JSONObject location = geometry.getJSONObject("location");
                lati = location.getString("lat");
                latitude = Double.parseDouble(lati);
//                    Log.v(LOG_TAG, "Google Latitude is: " + latitude);
                longi = location.getString("lng");
                longitude = Double.parseDouble(longi);
//                    Log.v(LOG_TAG, "Google Longitude is: " + longitude);

                if (currentRestaurant.has("rating")) {
                    rating = currentRestaurant.getDouble("rating");
                } else {
                    rating = 0.0;
                }


                // Extract the value for the key called "name"
                restaurantName = currentRestaurant.getString("name");

                //Extract the location of the restaurant from key "vicinity"
                restaurantLocation = currentRestaurant.getString("vicinity");

                //Extract the info if the restaurant is open or closed
//                if (currentRestaurant.has("opening_hours")){
//                    JSONObject opening_hours = currentRestaurant.getJSONObject("open_now");
//                    restaurantStatus = opening_hours.getBoolean("open_now");
//                }

                //Extract the tags of the restaurant
                if (currentRestaurant.has("types")) {
                    JSONArray types = currentRestaurant.getJSONArray("types");
                    for (int q = 0; q < types.length(); q++) {
                        restaurantTags = types.getString(q);
                    }
                }


                if (currentRestaurant.has("photos")) {
                    JSONArray photos = currentRestaurant.getJSONArray("photos");
                    for (int j = 0; j < photos.length(); j++) {
                        JSONObject photoObject = photos.getJSONObject(j);
                        imageResourceA = photoObject.getString("photo_reference");

                        imageResource = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + imageResourceA +
                                "&key=AIzaSyBDs67EpHkxvp3y3HETDsYrQXfe35hZ6hU";

                        Log.e(LOG_TAG, "Image error is" + imageResource);
                    }
                } else {
                    imageResource = "http://sulaindianrestaurant.com/wp-content/uploads/2013/07/menu-placeholder.gif";
                }


                // Create a new {@link Restaurant} object with the magnitude, location, time,
                // and url from the JSON response.
                Restaurant restaurant = new Restaurant(imageResource, restaurantName, restaurantLocation, 4.0, rating, latitude, longitude, restaurantStatus, restaurantTags);

                // Add the new {@link Restaurant} to the list of restaurants.
                restaurants.add(restaurant);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the restaurant JSON results", e);
        }

        // Return the list of restaurants
        return restaurants;
    }


}
