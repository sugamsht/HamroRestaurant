package com.project.hamrorestaurant.detail;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;
import com.project.hamrorestaurant.EditorActivity;
import com.project.hamrorestaurant.R;
import com.project.hamrorestaurant.data.CursorAdapter;
import com.project.hamrorestaurant.data.ReviewCursorAdapter;
import com.project.hamrorestaurant.data.RestaurantDBHelper;
import com.project.hamrorestaurant.login.AccountActivity;
import com.project.hamrorestaurant.login.LoginActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {

    RestaurantDBHelper mDbHelper;
    RestaurantDBHelper myDb;
    Cursor cursor;
    ReviewCursorAdapter reviewCursorAdapter;
    ListView listView;

    public ReviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_review, container, false);

        Button addReviewButton = rootView.findViewById(R.id.addReviewButton);
        addReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccessToken accessToken = AccountKit.getCurrentAccessToken();
                com.facebook.AccessToken loginToken = com.facebook.AccessToken.getCurrentAccessToken();
                if (accessToken != null || loginToken != null) {
                    // if previously logged in, proceed to the account activity
                    Intent intent = new Intent(getActivity(), EditorActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getActivity(), "Sign In to Continue", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        listView = rootView.findViewById(R.id.reviewList);
        mDbHelper = new RestaurantDBHelper(getActivity().getApplicationContext());
        cursor = mDbHelper.getReviewList();

        myDb = new RestaurantDBHelper(getActivity().getApplicationContext());
        reviewCursorAdapter = new ReviewCursorAdapter(getActivity().getApplicationContext(), cursor);

        listView.setAdapter(reviewCursorAdapter);

//        reviewCursorAdapter.notifyDataSetChanged();

        return rootView;
    }


}
