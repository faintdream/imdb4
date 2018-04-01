package com.akashdubey.imdb;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.akashdubey.imdb.adapter.MovieAdapter;
import com.akashdubey.imdb.adapter.UserMovieListAdapter;
import com.akashdubey.imdb.db.DbHelper;
import com.akashdubey.imdb.network.MyWebService;

import static com.akashdubey.imdb.db.Constants.ID;
import static com.akashdubey.imdb.db.Constants.IS_FAVOURITE;
import static com.akashdubey.imdb.db.Constants.IS_WATCHLIST;
import static com.akashdubey.imdb.db.Constants.POPULARITY;
import static com.akashdubey.imdb.db.Constants.POSTER_PATH;
import static com.akashdubey.imdb.db.Constants.RELEASE_DATE;
import static com.akashdubey.imdb.db.Constants.TABLE_NAME;
import static com.akashdubey.imdb.db.Constants.TITLE;
import static com.akashdubey.imdb.db.Constants.VOTE_AVERAGE;
import static com.akashdubey.imdb.db.Constants.VOTE_COUNT;
import static com.akashdubey.imdb.db.DbHelper.dbHelper;
import static com.akashdubey.imdb.db.DbHelper.sqLiteDatabase;

/**
 * This class handles display of user choice movie data from tmdb.
 * This is our Main Activity class.
 */

public class WebList extends MainActivity {


    public static MovieAdapter movieAdapter;
    public static MyWebService myWebService;
    public static RecyclerView recyclerView;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview1);
        if (dbHelper == null) {
            dbHelper = new DbHelper(this);
        }

        String type="";
        try{
            type = getIntent().getExtras().getString("type");

        }catch (RuntimeException e){
            e.printStackTrace();
                myWebService.getMostPopularMovies();
        }
        if (type==null) {
            type="xxx";
        } else {

            switch (type) {

                case "popular":
                    myWebService.getMostPopularMovies();
                    break;

                case "upcoming":
                    myWebService.getUpcomingMovies();
                    break;

                case "latest":
                    myWebService.getLatestMovies();
                    break;

                case "playing":
                    myWebService.getNowPlayingMovies();
                    break;

                case "toprated":
                    myWebService.getTopRatedMovies();
                    break;
            }

        }
    }
}

