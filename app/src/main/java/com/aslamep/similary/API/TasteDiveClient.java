package com.aslamep.similary.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TasteDiveClient {
    private static Retrofit retrofitInstance;
    private static Gson gson;

    public static Retrofit getInstance(){
        gson = new GsonBuilder()
                .setLenient()
                .create();

        if(retrofitInstance == null)
            retrofitInstance = new Retrofit.Builder()
                    .baseUrl("https://tastedive.com")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        return retrofitInstance;
    }

    public TasteDiveClient(){}
}
