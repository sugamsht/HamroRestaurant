package com.project.hamrorestaurant.detail;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.hamrorestaurant.R;
import com.project.hamrorestaurant.data.CursorAdapter;
import com.project.hamrorestaurant.data.RestaurantDBHelper;
import com.project.hamrorestaurant.data.RestaurantEntry;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends Fragment {

    RestaurantDBHelper mDbHelper;
    RestaurantDBHelper myDb;
    Cursor cursor;
    ListView listView;
    CursorAdapter menuCursorAdapter;


    public MenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);


        listView = rootView.findViewById(R.id.menuList);
        mDbHelper = new RestaurantDBHelper(getActivity().getApplicationContext());
        cursor = mDbHelper.getMenuList();


        myDb = new RestaurantDBHelper(getActivity().getApplicationContext());
        menuCursorAdapter = new CursorAdapter(getActivity().getApplicationContext(), cursor);

        listView.setAdapter(menuCursorAdapter);

        if (menuCursorAdapter == null) {
            insertMenu();
        }


        return rootView;
    }

    public void insertMenu() {
        RestaurantDBHelper mDbHelper = new RestaurantDBHelper(getActivity());

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(RestaurantEntry.COLUMN_MENU_ITEM_NAME, "MOMO");
        values.put(RestaurantEntry.COLUMN_MENU_PRICE, 70);

        long newRowId = db.insert(RestaurantEntry.TABLE_NAME, null, values);

    }

}


