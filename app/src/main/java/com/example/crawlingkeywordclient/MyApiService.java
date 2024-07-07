package com.example.crawlingkeywordclient;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyApiService {
    @GET("/titles/new")
    Call<NewTitlesResponse> getNewTitles(@Query("secretKey") String secretKey);
}
