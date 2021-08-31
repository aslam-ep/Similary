package com.aslamep.similary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aslamep.similary.API.OmdbAPI;
import com.aslamep.similary.API.OmdbClient;
import com.aslamep.similary.API.TasteDiveAPI;
import com.aslamep.similary.API.TasteDiveClient;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextInputEditText movieName;
    Button findMovies;
    ListView similarMovieList;
    ProgressBar loadingView;
    TextView quoteText;

    TasteDiveAPI tasteDiveAPI;
    OmdbAPI omdbAPI;

    String TASTE_DIVE_API = "383937-MovieFin-7XRETLZZ";
    String OMDB_API_KEY = "f2c0d5ef";

    // Result variable
    String [] relatedMovies;
    ArrayList<OmdbResponse> movieDetails;
    AdapterMovieList adapterMovieList;
    Dialog moviePopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapView();

        tasteDiveAPI = TasteDiveClient.getInstance().create(TasteDiveAPI.class);
        omdbAPI = OmdbClient.getInstance().create(OmdbAPI.class);
        movieDetails = new ArrayList<>();
        moviePopup = new Dialog(MainActivity.this);

        findMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String movie = movieName.getText().toString();
                if(movie.isEmpty()){
                    movieName.setError("Name required");
                } else{
                    hideKeyboard(MainActivity.this);

                    similarMovieList.setVisibility(View.INVISIBLE);
                    quoteText.setVisibility(View.INVISIBLE);
                    loadingView.setVisibility(View.VISIBLE);
                    findMovies.setEnabled(false);

                    movieDetails.clear();
                    similarMovieList.deferNotifyDataSetChanged();

                    Call<TasteDiveResponse> call = tasteDiveAPI.getMovies(movie, "10","1", TASTE_DIVE_API);
                    call.enqueue(new Callback<TasteDiveResponse>() {
                        @Override
                        public void onResponse(Call<TasteDiveResponse> call, Response<TasteDiveResponse> response) {
                            Log.d("Movie", response.toString());
                            int lenArr = response.body().Similar.Results.size();
                            if(lenArr == 0){
                                quoteText.setVisibility(View.VISIBLE);
                                loadingView.setVisibility(View.INVISIBLE);
                                findMovies.setEnabled(true);

                                Snackbar.make(findViewById(android.R.id.content), "Can't Find Movie / TV Show!", Snackbar.LENGTH_SHORT).show();
                            }else{
                                relatedMovies = new String[lenArr];

                                // Assigning the related movie names
                                for(int i=0; i < lenArr; i++){
                                    relatedMovies[i] = (response.body().Similar.Results).get(i).Name;
                                    Log.d("Movie", relatedMovies[i]);
                                    // From here we are calling omdb to get the ratings then sort the data
                                    getTheMovieDetails(relatedMovies[i], (response.body().Similar.Results).get(i).yID, i, lenArr);
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<TasteDiveResponse> call, Throwable t) {
                            quoteText.setVisibility(View.VISIBLE);
                            loadingView.setVisibility(View.INVISIBLE);
                            findMovies.setEnabled(true);

                            Snackbar.make(findViewById(android.R.id.content), "Check Your Connection!", Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void getTheMovieDetails(String relatedMovie, String trailer, int i, int lenArr) {
        Call<OmdbResponse> call = omdbAPI.getMovies(OMDB_API_KEY,relatedMovie,"json");
        call.enqueue(new Callback<OmdbResponse>() {
            @Override
            public void onResponse(Call<OmdbResponse> call, Response<OmdbResponse> response) {
                OmdbResponse movie = response.body();
                movie.Title = relatedMovie;
                movie.Trailer = trailer;

                movieDetails.add(movie);

                // Checking that all movies details are collected
                if(movieDetails.size() == lenArr){

                    // Sorting the movie based on IMDB rating
                    try {
                        Collections.sort(movieDetails, OmdbResponse.RatingComparator);
                    } catch (Exception ex){

                    }

                    // Creating array adapter for listView the related movies
                    adapterMovieList = new AdapterMovieList(movieDetails, MainActivity.this);
                    similarMovieList.setAdapter(adapterMovieList);

                    quoteText.setVisibility(View.INVISIBLE);
                    loadingView.setVisibility(View.INVISIBLE);
                    similarMovieList.setVisibility(View.VISIBLE);
                    findMovies.setEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<OmdbResponse> call, Throwable t) {
                quoteText.setVisibility(View.VISIBLE);
                loadingView.setVisibility(View.INVISIBLE);
                findMovies.setEnabled(true);

                Snackbar.make(findViewById(android.R.id.content), "Check Your Connection!", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void mapView() {
        movieName = findViewById(R.id.movie_name_to_find);
        findMovies = findViewById(R.id.find_button);
        similarMovieList = findViewById(R.id.movie_list);
        loadingView = findViewById(R.id.loading_view);
        quoteText = findViewById(R.id.quote_text);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}