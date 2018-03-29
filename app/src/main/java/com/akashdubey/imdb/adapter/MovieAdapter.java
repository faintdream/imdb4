package com.akashdubey.imdb.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akashdubey.imdb.DetailsScreen;
import com.akashdubey.imdb.MainActivity;
import com.akashdubey.imdb.R;
import com.akashdubey.imdb.WebList;
import com.akashdubey.imdb.model.MovieModel;
import com.akashdubey.imdb.network.MyWebService;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.akashdubey.imdb.WebList.myWebService;
import static com.akashdubey.imdb.network.MovieDetailsService.movieId;
import static com.akashdubey.imdb.network.MyWebService.URL_RATE_MOVIE_P1;
import static com.akashdubey.imdb.network.MyWebService.URL_RATE_MOVIE_P2;
import static com.akashdubey.imdb.network.MyWebService.guestSessionId;

//import static com.akashdubey.imdb.MainActivity.movieAdapter;

/**
 * Created by homepc on 07-03-2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyHolder> {

    private List<MovieModel> movieAdapterItem;
    WebList webList = new WebList();

    public MovieAdapter(List<MovieModel> movieModelList) {
        this.movieAdapterItem = movieModelList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.compact_view, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {

        final MovieModel movieModel = movieAdapterItem.get(position);
        Glide.with(holder.movieImage.getContext()).load(movieModel.getmMovieImage()).into(holder.movieImage);
        holder.movieTitle.setText(movieModel.getmTitle());
        holder.releaseDate.setText(movieModel.getmReleaseDate());
        holder.votesCount.setText(movieModel.getmVoteAverage() + "/ 10 voted by " + movieModel.getmVotesCount() + " ");
        holder.ratings.setRating(Float.parseFloat(movieModel.getmVoteAverage().toString()) / 2);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("LEGO", "ID " + movieModel.getmId() + ", NAME " + movieModel.getmTitle());
                jumpScreen(holder.itemView, movieModel.getmId().toString());
            }
        });

        holder.giveRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog showMovieRank;
                RatingBar movieRank;
                Toast.makeText(holder.itemView.getContext(), "You clicked on : " + movieModel.getmId(), Toast.LENGTH_SHORT).show();
                myWebService = new MyWebService();
                Log.i("LEGO", "Guest session ID: " + guestSessionId);
                showMovieRank = new Dialog(holder.itemView.getContext());
                showMovieRank.setTitle(holder.movieTitle.getText().toString());

                showMovieRank.setContentView(R.layout.rating_dialog);
                movieRank = (RatingBar) showMovieRank.findViewById(R.id.rateMovieRB);
                movieRank.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        String movieId = movieModel.getmId();
                        final String url = URL_RATE_MOVIE_P1 + movieId + URL_RATE_MOVIE_P2 + guestSessionId;
                        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
                        Map<String, Float> params = new HashMap<>();
                        params.put("value", v);
                        JSONObject jsonObject = new JSONObject(params);
                        OkHttpClient okHttpClient = new OkHttpClient();
                        RequestBody body = RequestBody.create(mediaType, params.toString());
                        Request request = new Request.Builder()
                                .url(url)
                                .post(body)
                                .addHeader("content-type", "application/json; charset=utf-8")
                                .build();
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                webList.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(holder.itemView.getContext(), "Try Later", Toast.LENGTH_SHORT).show();
                                        Log.i("LEGO", "BAD User Rating URL : " + url);
                                    }
                                });

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                webList.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(holder.itemView.getContext(), "Success", Toast.LENGTH_SHORT).show();
                                        Log.i("LEGO", "GOOD User Rating URL : " + url);
                                    }
                                });
                                String myResponse = response.body().string().toString();
                                try {
                                    JSONObject jsonObject1 = new JSONObject(myResponse);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        ratingBar.setRating(v);
                        Log.i("LEGO", "Rating : " + v);
                        showMovieRank.cancel();
                    }
                });
                showMovieRank.show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return MovieModel.movieModelList.size();
    }


    public class MyHolder extends RecyclerView.ViewHolder {
        RatingBar ratings;
        ImageView movieImage, giveRating;
        TextView movieTitle, releaseDate, popularity, votesCount;

        public MyHolder(View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.posterIV);
            movieTitle = itemView.findViewById(R.id.movieNameTV);
            releaseDate = itemView.findViewById(R.id.releasedateTV);
            votesCount = itemView.findViewById(R.id.votesTV);
            ratings = itemView.findViewById(R.id.ratingsRB);
            giveRating = itemView.findViewById(R.id.starIconIV);
        }


    }

    public void jumpScreen(View view, String movieId) {
        Intent intent = new Intent(view.getContext(), DetailsScreen.class);
        Bundle bundle = new Bundle();
        bundle.putString("movieId", movieId);
        intent.putExtras(bundle);
        view.getContext().startActivity(intent);

    }
}


