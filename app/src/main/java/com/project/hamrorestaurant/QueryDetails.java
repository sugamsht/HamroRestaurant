package com.project.hamrorestaurant;

import android.text.TextUtils;
import android.util.Log;

import com.project.hamrorestaurant.detail.RestaurantDetail;

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
 * Created by sugam on 8/26/2017.
 */


public class QueryDetails {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryDetails.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryDetails} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryDetails (and an object instance of QueryDetails is not needed).
     */
    private QueryDetails() {
    }

    /**
     * Query the Google API and return a list of {@link Restaurant} objects.
     */
    public static List<RestaurantDetail> fetchRestaurantData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);
        Log.e(LOG_TAG, "The parsable detail url is " + url);


        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        // Extract relevant fields from the JSON response and create a list of {@link Restaurant}s
        List<RestaurantDetail> restaurants = extractFeatureFromJson(jsonResponse);

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
     * Return a list of {@link RestaurantDetail} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<RestaurantDetail> extractFeatureFromJson(String restaurantJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(restaurantJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding restaurants to
        List<RestaurantDetail> restaurants = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(restaurantJSON);

            //for detail activities
            JSONObject detailrestaurant = baseJsonResponse.getJSONObject("result");

            JSONArray reviewsArray = detailrestaurant.getJSONArray("reviews");

            for (int i = 0; i < reviewsArray.length(); i++) {
                String author_name = null;
                String author_url = null;
                String profile_photo_url = null;
                Double rating = null;
                String relative_time_description = null;
                String text = null;

                JSONObject currentReview = reviewsArray.getJSONObject(i);

                author_name = currentReview.getString("author_name");
                Log.e(LOG_TAG, "The real author name is " + author_name);

                author_url = currentReview.getString("author_url");
                profile_photo_url = currentReview.getString("profile_photo_url");
                rating = currentReview.getDouble("rating");
                relative_time_description = currentReview.getString("relative_time_description");
                text = currentReview.getString("text");

                RestaurantDetail restaurantDetail = new RestaurantDetail(author_name);

                restaurants.add(restaurantDetail);
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryDetails", "Problem parsing the restaurant JSON results", e);
        }

        // Return the list of restaurants
        return restaurants;
    }


}
