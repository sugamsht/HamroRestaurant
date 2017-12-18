package com.project.hamrorestaurant.detail;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sugam on 11/1/2017.
 */

public class RestaurantDetail implements Parcelable {
    public static final Creator<RestaurantDetail> CREATOR = new Creator<RestaurantDetail>() {

        @Override
        public RestaurantDetail createFromParcel(Parcel in) {
            return new RestaurantDetail(in);
        }

        @Override
        public RestaurantDetail[] newArray(int size) {
            return new RestaurantDetail[size];
        }
    };
    private String mauthorName;

    protected RestaurantDetail(Parcel in) {
    }

    public RestaurantDetail(String authorName) {
        mauthorName = authorName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public String getAuthorName() {
        return mauthorName;
    }
}
