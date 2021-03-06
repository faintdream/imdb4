package com.akashdubey.imdb.db;

import android.database.Cursor;

/**
 * Created by homepc on 20-03-2018.
 *
 * While the class name is Constants, now it also contains a few variables,
 * I figured this is the best place to keep them.
 */

public class Constants {
    public static  final  Integer DB_VERSION=2;
    public static final String DB_NAME="tmdb.db";
    public static final String TABLE_NAME="movie_details";
    public static final String ID="id";
    public static final String TITLE="movie_title";
    public static final String RELEASE_DATE="release";
    public static final String POSTER_PATH="poster_path";
    public static final String POPULARITY="popularity";
    public static final String VOTE_AVERAGE="vote_average";
    public static final String VOTE_COUNT="vote_count";
    public static final String IS_WATCHLIST="is_watchlist";
    public static final String IS_FAVOURITE="is_favourite";

    public static boolean runOnce=false;
    public static boolean refreshStatus=false;
    public static Cursor userMovieListcursorFavourite,
            userMovieListcursorWatchLater,
            getUserMovieListcursorRefresh;

}
