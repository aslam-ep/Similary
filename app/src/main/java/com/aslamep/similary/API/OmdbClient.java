package com.aslamep.similary.API;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class OmdbClient {
    private static Retrofit retrofitInstance;

    public static Retrofit getInstance(){

        if(retrofitInstance == null)
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl("https://www.omdbapi.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return retrofitInstance;
    }

    public OmdbClient(){}
}
