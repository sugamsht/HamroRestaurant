package com.project.hamrorestaurant;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sugam on 8/21/2017.
 */

public class Restaurant implements Parcelable {

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
    /** Constant value that represents no image was provided for this word */
    private static final String NO_IMAGE_PROVIDED = null;
    /**
     * Image resource ID for the word
     */
    private String mImageResource = NO_IMAGE_PROVIDED;
    private String mRestaurantName;
    private String mRestaurantLocation;
    private Double mDistance;

    /**
     * Create new Restaurant Object
     *
     * @param imageResource is the drawable resource ID for the image associated with the restaurant
     * @param restaurantName is the name of the restaurant
     * @param restaurantLocation is the location of the restaurant
     */
    public Restaurant(String imageResource, String restaurantName, String restaurantLocation, Double distance) {
        mImageResource = imageResource;
        mRestaurantName = restaurantName;
        mRestaurantLocation = restaurantLocation;
        mDistance = distance;

    }

    protected Restaurant(Parcel in) {
        mImageResource = in.readString();
        mRestaurantName = in.readString();
        mRestaurantLocation = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mImageResource);
        dest.writeString(mRestaurantName);
        dest.writeString(mRestaurantLocation);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Return the image resource ID of the restaurant.
     */
    public String getImageResource() {
        return mImageResource;
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
        return mImageResource != NO_IMAGE_PROVIDED;
    }


}
