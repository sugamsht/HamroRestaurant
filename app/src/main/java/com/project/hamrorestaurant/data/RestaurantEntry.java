package com.project.hamrorestaurant.data;

import android.provider.BaseColumns;

/**
 * Created by sugam on 11/22/2017.
 */

public class RestaurantEntry implements BaseColumns {

    public final static String TABLE_NAME = "menu";

    public final static String KEY_ROWID = "_id";

    public final static String _ID = BaseColumns._ID;

    public final static String COLUMN_MENU_ITEM_NAME = "name";

    public final static String COLUMN_MENU_PRICE = "price";

    //for table review
    public final static String ID = "_id";
    public final static String TABLE_REVIEW = "review";
    public final static String COLUMN_REVIEW_NAME = "name";
    public final static String COLUMN_REVIEW_COMMENT = "comment";
    public final static String COLUMN_REVIEW_RATING = "rating";


}

