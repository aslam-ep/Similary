package com.aslamep.similary.API;

import com.aslamep.similary.OmdbResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OmdbAPI {
    @GET("/")
    Call<OmdbResponse> getMovies(@Query("apikey") String apiKey, @Query("t") String movieName, @Query("r") String type);
}
