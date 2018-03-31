package com.akashdubey.imdb;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import static com.akashdubey.imdb.db.Constants.*;

import com.akashdubey.imdb.adapter.UserMovieListAdapter;


import static com.akashdubey.imdb.db.DbHelper.dbHelper;
import static com.akashdubey.imdb.db.DbHelper.sqLiteDatabase;

/**
 * This class handles display of user choice movie data
 */

public class UserMovieList extends MainActivity {
    MainActivity mainActivity = new MainActivity();

    RecyclerView umlRV;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_movie_list_view);
        umlRV = findViewById(R.id.umlRV);


        String status = getIntent().getExtras().getString("refresh");
        //if we received a refresh action and corresponding cursort not empty
        // display it
        if (status.equals("yes") && getUserMovieListcursorRefresh != null) {
            publishResultUserListRefresh(getUserMovieListcursorRefresh);
        } else {

            // or check what is passed eother favourite or watch later
            String category = getIntent().getExtras().getString("search");
            String[] args = {"yes"};
            dbHelper.openConnection();

            //hop a step back if category is empty, cheap way thou
            if (category == null) {
                Toast.makeText(this, "No updates", Toast.LENGTH_SHORT).show();
                onBackPressed();


            } else {
                switch (category) {
                    case "favourites":
                        if (userMovieListcursorFavourite == null) {
                            userMovieListcursorFavourite = sqLiteDatabase.query(TABLE_NAME,
                                    new String[]{ID, TITLE, RELEASE_DATE, POSTER_PATH, POPULARITY, VOTE_AVERAGE,
                                            VOTE_COUNT, IS_FAVOURITE, IS_WATCHLIST}, IS_FAVOURITE + "=?"
                                    , args, null, null, null);
                            if (userMovieListcursorFavourite.getCount() > 0) {
                                publishResultUserList(userMovieListcursorFavourite);
                            } else {
                                Toast.makeText(this, "No updates", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        } else {
                            publishResultUserList(userMovieListcursorFavourite);
                            getUserMovieListcursorRefresh = sqLiteDatabase.query(TABLE_NAME,
                                    new String[]{ID, TITLE, RELEASE_DATE, POSTER_PATH, POPULARITY, VOTE_AVERAGE,
                                            VOTE_COUNT, IS_FAVOURITE, IS_WATCHLIST}, IS_FAVOURITE + "=?"
                                    , args, null, null, null);
                        }

                        break;
                    case "watchlater":
                        if (userMovieListcursorWatchLater == null) {
                            userMovieListcursorWatchLater = sqLiteDatabase.query(TABLE_NAME,
                                    new String[]{ID, TITLE, RELEASE_DATE, POSTER_PATH, POPULARITY, VOTE_AVERAGE,
                                            VOTE_COUNT, IS_FAVOURITE, IS_WATCHLIST}, IS_WATCHLIST + "=?"
                                    , args, null, null, null);
                            if (userMovieListcursorWatchLater.getCount() > 0) {
                                publishResultUserList(userMovieListcursorWatchLater);
                            } else {
                                Toast.makeText(this, "No updates", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            }
                        } else {
                            publishResultUserList(userMovieListcursorWatchLater);
                            getUserMovieListcursorRefresh = sqLiteDatabase.query(TABLE_NAME,
                                    new String[]{ID, TITLE, RELEASE_DATE, POSTER_PATH, POPULARITY, VOTE_AVERAGE,
                                            VOTE_COUNT, IS_FAVOURITE, IS_WATCHLIST}, IS_WATCHLIST + "=?"
                                    , args, null, null, null);
                        }
                        break;

                    case "refresh":
                        publishResultUserListRefresh(getUserMovieListcursorRefresh);
                        break;
                    default:
                        Toast.makeText(this, "No updates", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                        break;

                }


            }

        }
    }


    private void publishResultUserList(Cursor cursor) {
        UserMovieListAdapter userMovieListAdapter = new UserMovieListAdapter(cursor);
        umlRV.setLayoutManager(new LinearLayoutManager(mainActivity));
        umlRV.setAdapter(userMovieListAdapter);
    }

    private void publishResultUserListRefresh(Cursor cursor) {
        UserMovieListAdapter userMovieListAdapter = new UserMovieListAdapter(cursor);
        umlRV.setLayoutManager(new LinearLayoutManager(mainActivity));
        userMovieListAdapter.notifyDataSetChanged();
        umlRV.setAdapter(userMovieListAdapter);
    }

}
