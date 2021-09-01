package com.aslamep.similary;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;

public class AdapterMovieList implements ListAdapter {

    List<OmdbResponse> movieList;
    Context context;
    Dialog moviePopup;

    public AdapterMovieList(List<OmdbResponse> movieList, Context context) {
        this.movieList = movieList;
        this.context = (Context) context;
        moviePopup = new Dialog(context);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OmdbResponse movie = movieList.get(position);

        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.movie_listing, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    moviePopup.setContentView(R.layout.movie_details);

                    OmdbResponse popMovie = movieList.get(position);

                    ImageView poster;
                    TextView title, year, runTime, imdbRating, tomatoRating, plot, closeButton, genre;
                    Button trailerButton;

                    // Mapping
                    poster = moviePopup.findViewById(R.id.poster);
                    title = moviePopup.findViewById(R.id.movie_name);
                    year = moviePopup.findViewById(R.id.year);
                    runTime = moviePopup.findViewById(R.id.runtime);
                    imdbRating = moviePopup.findViewById(R.id.imdb);
                    tomatoRating = moviePopup.findViewById(R.id.rotten);
                    plot = moviePopup.findViewById(R.id.plot);
                    genre = moviePopup.findViewById(R.id.genre);
                    closeButton = moviePopup.findViewById(R.id.close_button);
                    trailerButton = moviePopup.findViewById(R.id.trailer_button);

                    // Assigning
                    Glide.with(context)
                            .load(popMovie.Poster)
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)         //ALL or NONE as your requirement
                            .thumbnail(Glide.with(context).load(R.drawable.ic_baseline_image_24))
                            .error(R.drawable.ic_baseline_broken_image_24)
                            .into(poster);

                    title.setText(popMovie.Title);
                    year.setText(popMovie.Year);
                    runTime.setText(popMovie.Runtime);
                    genre.setText(popMovie.Genre);
                    imdbRating.setText(popMovie.imdbRating);

                    if (popMovie.Trailer == null){
                        trailerButton.setEnabled(false);
                    }

                    String rottenRating = "N/A";
                    for (int i=0; i < popMovie.Ratings.size(); i++){
                        if (popMovie.Ratings.get(i).Source.equalsIgnoreCase("Rotten Tomatoes")) {
                            rottenRating = popMovie.Ratings.get(i).Value;
                        }
                    }
                    tomatoRating.setText(rottenRating);
                    plot.setText(popMovie.Plot);

                    closeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            moviePopup.dismiss();
                        }
                    });

                    trailerButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            watchYoutubeVideo(context, popMovie.Trailer);
                        }
                    });

                    moviePopup.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    moviePopup.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    moviePopup.show();
                }
            });
            TextView movieName = convertView.findViewById(R.id.movie_name_list);
            TextView movieYear = convertView.findViewById(R.id.movie_year_list);
            ImageView moviePoster=convertView.findViewById(R.id.movie_poster_list);

            movieName.setText(movie.Title);
            movieYear.setText(movie.Year);

            Glide.with(context)
                    .load(movie.Poster)
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)         //ALL or NONE as your requirement
                    .thumbnail(Glide.with(context).load(R.drawable.ic_baseline_image_24))
                    .error(R.drawable.ic_baseline_broken_image_24)
                    .into(moviePoster);
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return movieList.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    public static void watchYoutubeVideo(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
    }
}
