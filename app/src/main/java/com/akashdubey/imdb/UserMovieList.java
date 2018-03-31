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
 * This class handles display of user choice movie data data
 */

public class UserMovieList extends MainActivity {
    MainActivity mainActivity = new MainActivity();

    RecyclerView umlRV;
//    public static Cursor cursor;


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

        if (status.equals("yes") && getUserMovieListcursorRefresh != null) {
            publishResultUserListRefresh(getUserMovieListcursorRefresh);
        } else {
            String category = getIntent().getExtras().getString("search");
            String[] args = {"yes"};
            dbHelper.openConnection();
            if (category == null) {
                Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT).show();
            } else {
                switch (category) {
                    case "favourites":
                        if(userMovieListcursorFavourite==null){
                        userMovieListcursorFavourite = sqLiteDatabase.query(TABLE_NAME,
                                new String[]{ID, TITLE, RELEASE_DATE, POSTER_PATH, POPULARITY, VOTE_AVERAGE,
                                        VOTE_COUNT, IS_FAVOURITE, IS_WATCHLIST}, IS_FAVOURITE + "=?"
                                , args, null, null, null);
                        publishResultUserList(userMovieListcursorFavourite);
                        }else{
                            publishResultUserList(userMovieListcursorFavourite);
                            getUserMovieListcursorRefresh=sqLiteDatabase.query(TABLE_NAME,
                                    new String[]{ID, TITLE, RELEASE_DATE, POSTER_PATH, POPULARITY, VOTE_AVERAGE,
                                            VOTE_COUNT, IS_FAVOURITE, IS_WATCHLIST}, IS_FAVOURITE + "=?"
                                    , args, null, null, null);
                        }

                        break;
                    case "watchlater":
                        userMovieListcursorWatchLater = sqLiteDatabase.query(TABLE_NAME,
                                new String[]{ID, TITLE, RELEASE_DATE, POSTER_PATH, POPULARITY, VOTE_AVERAGE,
                                        VOTE_COUNT, IS_FAVOURITE, IS_WATCHLIST}, IS_WATCHLIST + "=?"
                                , args, null, null, null);
                        break;

                    case "refresh":
                        publishResultUserListRefresh(getUserMovieListcursorRefresh);
                        break;
                    default:
                        Toast.makeText(this, "Nothing to show", Toast.LENGTH_SHORT).show();
                        break;

                }

//                if (userMovieListcursor.getCount() < 1) {
//                    Toast.makeText(this, "Try adding some movies ", Toast.LENGTH_LONG).show();
//                } else {
//                    publishResultUserList(userMovieListcursor);


                }

            }
        }

//    }


    private void publishResultUserList(Cursor cursor) {
        UserMovieListAdapter userMovieListAdapter = new UserMovieListAdapter(cursor);
        umlRV.setLayoutManager(new LinearLayoutManager(mainActivity));
//        userMovieListAdapter.notifyDataSetChanged();
        umlRV.setAdapter(userMovieListAdapter);
    }

    private void publishResultUserListRefresh(Cursor cursor) {
        UserMovieListAdapter userMovieListAdapter = new UserMovieListAdapter(cursor);
        umlRV.setLayoutManager(new LinearLayoutManager(mainActivity));
        userMovieListAdapter.notifyDataSetChanged();
        umlRV.setAdapter(userMovieListAdapter);
    }

}
