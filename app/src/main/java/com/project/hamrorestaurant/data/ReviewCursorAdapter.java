package com.project.hamrorestaurant.data;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.project.hamrorestaurant.R;

/**
 * Created by sugam on 12/13/2017.
 */

public class ReviewCursorAdapter extends android.widget.CursorAdapter {

    private final static String LOG_TAG = ReviewCursorAdapter.class.getSimpleName();

    public ReviewCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.review_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = view.findViewById(R.id.authorName);
        TextView commentTextView = view.findViewById(R.id.comment);
        RatingBar ratingBar = view.findViewById(R.id.rating);

        int nameColumnIndex = cursor.getColumnIndex(RestaurantEntry.COLUMN_REVIEW_NAME);
        int commentColumnIndex = cursor.getColumnIndex(RestaurantEntry.COLUMN_REVIEW_COMMENT);
        int ratingColumnIndex = cursor.getColumnIndex(RestaurantEntry.COLUMN_REVIEW_RATING);

        String authorName = cursor.getString(nameColumnIndex);
        String comment = cursor.getString(commentColumnIndex);
        int rating = cursor.getInt(ratingColumnIndex);
        Log.v(LOG_TAG, "THe rating is " + rating);


        nameTextView.setText(authorName);
        commentTextView.setText(comment);
        ratingBar.setNumStars(rating);

    }
}