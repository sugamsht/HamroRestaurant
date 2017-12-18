package com.project.hamrorestaurant;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.facebook.Profile;
import com.project.hamrorestaurant.data.RestaurantDBHelper;
import com.project.hamrorestaurant.data.RestaurantEntry;
import com.project.hamrorestaurant.login.AccountActivity;

public class EditorActivity extends AppCompatActivity {

    public final static String LOG_TAG = EditorActivity.class.getSimpleName();
    /**
     * EditText field to enter the review comment
     */
    private EditText mCommentEditText;
    private RatingBar mRatingBar;
    private Button mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mCommentEditText = findViewById(R.id.edit_review_comment);

        mRatingBar = findViewById(R.id.edit_review_rating);

        mSubmitButton = findViewById(R.id.edit_submit_button);

//        addListenerOnRatingBar();

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertReview();
            }
        });
    }

//    public void addListenerOnRatingBar(){
//        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
//
//            }
//        });
//    }

    private void insertReview() {
        String commentString = mCommentEditText.getText().toString();
        int rating = mRatingBar.getNumStars();

        RestaurantDBHelper mDbHelper = new RestaurantDBHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

//        AccountActivity accountActivity = new AccountActivity();
//        String name = accountActivity.getAuthorName();
//        Log.v(LOG_TAG,"THe author name is " + name);

        String name = getAuthorName(Profile.getCurrentProfile());

        ContentValues values = new ContentValues();
        values.put(RestaurantEntry.COLUMN_REVIEW_COMMENT, commentString);
        values.put(RestaurantEntry.COLUMN_REVIEW_RATING, rating);
        values.put(RestaurantEntry.COLUMN_REVIEW_NAME, name);

        long newRowId = db.insert(RestaurantEntry.TABLE_REVIEW, null, values);


        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving review", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Review saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

//    private String getlaskd(){
//        AccountActivity accountActivity = new AccountActivity();
//        accountActivity.getAuthorName()
//        return name;
//    }

    public String getAuthorName(Profile profile) {
        String name = profile.getName();

        return name;
//        RestaurantDBHelper mDbHelper = new RestaurantDBHelper(this);

//        // Gets the database in write mode
//        SQLiteDatabase db = mDbHelper.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//
//        values.put(RestaurantEntry.COLUMN_REVIEW_NAME,name);
//
//        long newRowId = db.insert(RestaurantEntry.TABLE_REVIEW,null,values);


    }

}
