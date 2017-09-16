package com.project.hamrorestaurant;

import android.media.Rating;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sugam on 8/21/2017.
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
    private Double mRating;
    private Double mLatitude;
    private Double mLongitude;
    private String mopenNow;
    private String mTags;
    private String mWebsite;
    private Double mphoneNumber;


    /**
     * Create new Restaurant Object
     *
     * @param imageResource is the drawable resource ID for the image associated with the restaurant
     * @param restaurantName is the name of the restaurant
     * @param restaurantLocation is the location of the restaurant
     */
    public Restaurant(String imageResource, String restaurantName, String restaurantLocation, Double distance, Double rating,
                      Double latitude, Double longitude, String openNow, String tags) {
        mImageResource = imageResource;
        mRestaurantName = restaurantName;
        mRestaurantLocation = restaurantLocation;
        mDistance = distance;
        mRating = rating;
        mLatitude = latitude;
        mLongitude = longitude;
        mopenNow = openNow;
        mTags = tags;

    }

    protected Restaurant(Parcel in) {
        mImageResource = in.readString();
        mRestaurantName = in.readString();
        mRestaurantLocation = in.readString();
        mDistance = in.readDouble();
        mRating = in.readDouble();
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
        mopenNow = in.readString();
        mTags = in.readString();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mImageResource);
        dest.writeString(mRestaurantName);
        dest.writeString(mRestaurantLocation);
        dest.writeDouble(mDistance);
        dest.writeDouble(mRating);
        dest.writeDouble(mLatitude);
        dest.writeDouble(mLongitude);
        dest.writeString(mopenNow);
        dest.writeString(mTags);

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


    //Returns the distance between restautant and the user
    public Double getDistance(){
        return mDistance;
    }


    //Returns the rating of the restaurant
    public Double getRating() {
        return mRating;
    }

    //Returns the Latitude of the restaurant
    public Double getLatitude() {
        return mLatitude;
    }

    //Returns the Longitude of the restaurant
    public Double getLongitude() {
        return mLongitude;
    }

    //Returns if the of the restaurant is open or closed
    public String getOpenNow() {
        return mopenNow;
    }

    //Returns the tags of the restaurant
    public String getTags() {
        return mTags;
    }

    //Returns the website of the restaurant

    //Returns the phonenumber of the restaurant

    /**
     * Returns whether or not there is an image for this word.
     */
    public boolean hasImage() {
        return mImageResource != NO_IMAGE_PROVIDED;
    }


}
