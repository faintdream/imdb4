package com.akashdubey.imdb.network;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.akashdubey.imdb.DetailsScreen;
import com.akashdubey.imdb.MainActivity;
//import com.akashdubey.imdb.model.MovieAdapter;
import com.akashdubey.imdb.WebList;
import com.akashdubey.imdb.adapter.MovieAdapter;
import com.akashdubey.imdb.model.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



import static com.akashdubey.imdb.WebList.movieAdapter;
import static com.akashdubey.imdb.WebList.recyclerView;
import static com.akashdubey.imdb.model.MovieModel.movieModelList;
import static com.akashdubey.imdb.network.MovieDetailsService.movieId;
//import static com.akashdubey.imdb.model.MovieModel;

/**
 * Created by homepc on 07-03-2018.
 */

public class MyWebService {
    public static final String URL_UPCOMING_MOVIES =
            "http://api.themoviedb.org/3/movie/upcoming?api_key=8496be0b2149805afa458ab8ec27560c";
    public static final String URL_POPULAR_MOVIES =
            "http://api.themoviedb.org/3/movie/popular?api_key=8496be0b2149805afa458ab8ec27560c";
    public static final String URL_NOW_PALYING_MOVIES =
            "http://api.themoviedb.org/3/movie/now_playing?api_key=8496be0b2149805afa458ab8ec27560c";
    public static final String URL_LATEST_MOVIES =
            "http://api.themoviedb.org/3/movie/latest?api_key=8496be0b2149805afa458ab8ec27560c";
    public static final String URL_TOP_RATED_MOVIES =
            "http://api.themoviedb.org/3/movie/top_rated?api_key=8496be0b2149805afa458ab8ec27560c";
    public static final String URL_GUEST_SESSION_GENERATOR =
                    "https://api.themoviedb.org/3/authentication/guest_session/new?api_key=8496be0b2149805afa458ab8ec27560c";

    public static final String URL_RATE_MOVIE_P1 =
            "https://api.themoviedb.org/3/movie/" ;

    public static final String URL_RATE_MOVIE_P2=
            "/rating?api_key=8496be0b2149805afa458ab8ec27560c&guest_session_id=";

    public static final String VOTE_COUNT = "vote_count";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String POPULARITY = "popularity";
    public static final String MOVIE_IMAGE = "poster_path";
    public static final String MOVIE_TITLE = "title";
    public static final String RELEASE_DATE = "release_date";
    public static final String MOVIE_ID = "id";
    public static final String GUEST_SESSION_ID="guest_session_id";

    public static String guestSessionId="UNKNOWN";


//    MainActivity mainActivity = new MainActivity();
    WebList webList=new WebList();
    OkHttpClient okHttpClient = new OkHttpClient();
    Request request;
    JSONObject jsonObject;
    JSONArray jsonArray;

    public void getMostPopularMovies() {
        movieModelList.clear();
        String url = URL_POPULAR_MOVIES;
        request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String myResponse = response.body().string().toString();
                try {
                    jsonObject = new JSONObject(myResponse);
                    jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        String imgBaseUrl = "https://image.tmdb.org/t/p/w45" + jsonObject.getString(MOVIE_IMAGE);
                        movieModelList.add(new MovieModel(
                                        jsonObject.getString(MOVIE_TITLE),
                                        jsonObject.getString(RELEASE_DATE),
                                        jsonObject.getString(POPULARITY),
                                        jsonObject.getString(VOTE_COUNT),
                                        imgBaseUrl,
                                        jsonObject.getString(VOTE_AVERAGE),
                                        jsonObject.getString(MOVIE_ID)
                                )
                        );

                    }

                    webList.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            publishResult(movieModelList);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }

    public void getUpcomingMovies() {

        if(movieModelList.isEmpty()==false){
            movieModelList.clear();
        }

        String url = URL_UPCOMING_MOVIES;
        request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String myResponse = response.body().string().toString();
                try {
                    jsonObject = new JSONObject(myResponse);
                    jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        jsonObject = jsonArray.getJSONObject(i);
                        String imgBaseUrl = "https://image.tmdb.org/t/p/w185" + jsonObject.getString(MOVIE_IMAGE);
                        movieModelList.add(new MovieModel(
                                        jsonObject.getString(MOVIE_TITLE),
                                        jsonObject.getString(RELEASE_DATE),
                                        jsonObject.getString(POPULARITY),
                                        jsonObject.getString(VOTE_COUNT),
                                        imgBaseUrl,
                                        jsonObject.getString(VOTE_AVERAGE),
                                        jsonObject.getString(MOVIE_ID)
                                )
                        );
                    }

                    webList.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            publishResult(movieModelList);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void getLatestMovies() {
        movieModelList.clear();
        String url = URL_LATEST_MOVIES;
        request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String myResponse = response.body().string().toString();
                try {
                    jsonObject = new JSONObject(myResponse);
                    String imgBaseUrl = "https://image.tmdb.org/t/p/w185" + jsonObject.getString(MOVIE_IMAGE);
                    movieModelList.add(new MovieModel(
                            jsonObject.getString(MOVIE_TITLE),
                            jsonObject.getString(RELEASE_DATE),
                            jsonObject.getString(POPULARITY),
                            jsonObject.getString(VOTE_COUNT),
                            imgBaseUrl,
                            jsonObject.getString(VOTE_AVERAGE),
                            jsonObject.getString(MOVIE_ID))

                    );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                MainActivity mainActivity = new MainActivity();
                    webList.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            publishResult(movieModelList);
                        }
                    });
                }

        });
    }

    public void getNowPlayingMovies() {
        movieModelList.clear();
        String url = URL_NOW_PALYING_MOVIES;
        request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String myResponse = response.body().string().toString();
                try {
                    jsonObject = new JSONObject(myResponse);
                    jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        jsonObject = jsonArray.getJSONObject(i);
                        String imgBaseUrl = "https://image.tmdb.org/t/p/w185" + jsonObject.getString(MOVIE_IMAGE);
                        movieModelList.add(new MovieModel(
                                        jsonObject.getString(MOVIE_TITLE),
                                        jsonObject.getString(RELEASE_DATE),
                                        jsonObject.getString(POPULARITY),
                                        jsonObject.getString(VOTE_COUNT),
                                        imgBaseUrl,
                                        jsonObject.getString(VOTE_AVERAGE), jsonObject.getString(MOVIE_ID)
                                )
                        );
                    }

                    webList.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            publishResult(movieModelList);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


    }



    public void getTopRatedMovies() {
        movieModelList.clear();
        String url = URL_TOP_RATED_MOVIES;
        request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String myResponse = response.body().string().toString();
                try {
                    jsonObject = new JSONObject(myResponse);
                    jsonArray = jsonObject.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        jsonObject = jsonArray.getJSONObject(i);
                        String imgBaseUrl = "https://image.tmdb.org/t/p/w185" + jsonObject.getString(MOVIE_IMAGE);
                        movieModelList.add(new MovieModel(
                                        jsonObject.getString(MOVIE_TITLE),
                                        jsonObject.getString(RELEASE_DATE),
                                        jsonObject.getString(POPULARITY),
                                        jsonObject.getString(VOTE_COUNT),
                                        imgBaseUrl,
                                        jsonObject.getString(VOTE_AVERAGE),
                                        jsonObject.getString(MOVIE_ID)
                                )
                        );
                    }

                    webList.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            publishResult(movieModelList);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void getGuestSessionID(){

        String url=URL_GUEST_SESSION_GENERATOR;
        request=new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String myResponse=response.body().string().toString();
                try{
                    jsonObject=new JSONObject(myResponse);
                    guestSessionId=jsonObject.get(GUEST_SESSION_ID).toString();
                    Log.i("LEGO","Guest session ID :"+ guestSessionId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getGuestSessionRatings(){

    }

    public void setRatingForThisMovie(String movieId){
        if(!guestSessionId.equals("TBD")){
            String url=URL_RATE_MOVIE_P1+movieId+URL_RATE_MOVIE_P2+guestSessionId;
            request=new Request.Builder().url(url).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                        String myResponse=response.body().string().toString();
                }
            });
        }
    }

    private void publishResult(List<MovieModel> movieModelList) {

        movieAdapter = new MovieAdapter(movieModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(webList));
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();


    }
}
