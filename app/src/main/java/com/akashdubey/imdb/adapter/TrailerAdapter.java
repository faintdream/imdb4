package com.akashdubey.imdb.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akashdubey.imdb.R;
import com.akashdubey.imdb.model.MovieDetailsModel;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by homepc on 13-03-2018.
 * This class displays Trailer text in horizontal scrolling
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder> {
    List<MovieDetailsModel> trailerAdapterList= new ArrayList<>();
    public TrailerAdapter(List<MovieDetailsModel> trailerModelList) {
        this.trailerAdapterList=trailerModelList;
    }

    @Override
    public TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_view,parent,false);

        return new TrailerHolder(view);
    }

    @Override
    public void onBindViewHolder(final TrailerHolder holder, int position) {
        final MovieDetailsModel movieDetailsModel=trailerAdapterList.get(position);
        holder.trailer.setText(movieDetailsModel.getmTrailerName());

        holder.trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context=holder.itemView.getContext();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + movieDetailsModel.getmTrailerKey()));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return trailerAdapterList.size();
    }

    public class TrailerHolder extends RecyclerView.ViewHolder{
        TextView trailer;
        public TrailerHolder(View itemView)
        {
            super(itemView);
            trailer=itemView.findViewById(R.id.dtlTrailerTV);

        }
    }
}
