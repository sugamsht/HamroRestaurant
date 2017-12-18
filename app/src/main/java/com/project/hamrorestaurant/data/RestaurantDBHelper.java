package com.project.hamrorestaurant.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.hamrorestaurant.Restaurant;

/**
 * Created by sugam on 11/22/2017.
 */

public class RestaurantDBHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = RestaurantDBHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "restaurantdb.db";
    private static final int DATABASE_VERSION = 1;


    public RestaurantDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_MENU_TABLE = "CREATE TABLE " + RestaurantEntry.TABLE_NAME + " ("
                + RestaurantEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RestaurantEntry.COLUMN_MENU_ITEM_NAME + " TEXT NOT NULL, "
                + RestaurantEntry.COLUMN_MENU_PRICE + " REAL NOT NULL DEFAULT 0);";

        //Create review table
        String SQL_CREATE_REVIEW_TABLE = "CREATE TABLE if not exists " + RestaurantEntry.TABLE_REVIEW + " ("
                + RestaurantEntry.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RestaurantEntry.COLUMN_REVIEW_NAME + " TEXT, "
                + RestaurantEntry.COLUMN_REVIEW_COMMENT + " TEXT, "
                + RestaurantEntry.COLUMN_REVIEW_RATING + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_MENU_TABLE);

        db.execSQL(SQL_CREATE_REVIEW_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop table if exists " + RestaurantEntry.TABLE_NAME);
//        db.execSQL("drop table if exists " + RestaurantEntry.TABLE_REVIEW);
//        onCreate(db);
    }

    public Cursor getMenuList() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + RestaurantEntry.TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }

        return cursor;
    }

    public Cursor getReviewList() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + RestaurantEntry.TABLE_REVIEW;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;

    }

}
