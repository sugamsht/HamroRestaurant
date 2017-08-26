package com.project.hamrorestaurant;

/**
 * Created by Sugam on 8/21/2017.
 */

public class Restaurant {

    /** Image resource ID for the word */
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    /** Constant value that represents no image was provided for this word */
    private static final int NO_IMAGE_PROVIDED = -1;

    private String mRestaurantName;


    private String mRestaurantLocation;

    private Double mDistance;

    /**
     * Create new Restaurant Object
     *
     * @param imageResourceId is the drawable resource ID for the image associated with the restaurant
     * @param restaurantName is the name of the restaurant
     * @param restaurantLocation is the location of the restaurant
     */
    public Restaurant(int imageResourceId, String restaurantName, String restaurantLocation, Double distance){
        mImageResourceId = imageResourceId;
        mRestaurantName = restaurantName;
        mRestaurantLocation = restaurantLocation;
        mDistance = distance;

    }

    /**
     * Return the image resource ID of the restaurant.
     */
    public int getImageResourceId() {
        return mImageResourceId;
    }

    /**
     * Return the name of the restaurant.
     */
    public String getRestaurantName(){
        return mRestaurantName;
    }

    /**
     * Return the location of the restaurant.
     */

    public String getRestaurantLocation(){
        return mRestaurantLocation;
    }

    public Double getDistance(){
        return mDistance;
    }

    /**
     * Returns whether or not there is an image for this word.
     */
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }


}
