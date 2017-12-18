package com.project.hamrorestaurant.data;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.hamrorestaurant.R;

/**
 * Created by sugam on 11/22/2017.
 */

public class CursorAdapter extends android.widget.CursorAdapter {

    private final static String LOG_TAG = CursorAdapter.class.getSimpleName();

    public CursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.menu_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nameTextView = view.findViewById(R.id.foodName);
        TextView priceTextView = view.findViewById(R.id.foodPrice);

        int nameColumnIndex = cursor.getColumnIndex(RestaurantEntry.COLUMN_MENU_ITEM_NAME);
        Log.v(LOG_TAG, "The column index of food is " + nameColumnIndex);
        int locationColumnIndex = cursor.getColumnIndex(RestaurantEntry.COLUMN_MENU_PRICE);

        String foodName = cursor.getString(nameColumnIndex);
        String foodPrice = cursor.getString(locationColumnIndex);

        String fp = String.valueOf(foodPrice);

        nameTextView.setText(foodName);
        priceTextView.setText(fp);

    }
}
