package com.aslamep.similary.API;

import com.aslamep.similary.TasteDiveResponse;

import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface TasteDiveAPI {

    @GET("api/similar")
    Call<TasteDiveResponse> getMovies(@Query("q") String apiKey, @Query("limit") String movieName, @Query("info") String info, @Query("k") String type);
}
