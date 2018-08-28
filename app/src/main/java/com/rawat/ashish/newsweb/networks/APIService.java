package com.rawat.ashish.newsweb.networks;


import com.rawat.ashish.newsweb.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("top-headlines")
    Call<News> topNews(@Query("country") String country,
                       @Query("apiKey") String apiKey);

    @GET("top-headlines")
    Call<News> topNewsCategory(@Query("country") String country,
                               @Query("category") String category,
                               @Query("apiKey") String apiKey);


}