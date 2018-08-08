package com.example.bolek.testy.interfaces;

import com.example.bolek.testy.pojo.Chapter;
import retrofit.Callback;
import retrofit.http.GET;

public interface WebService {
    @GET("/novelki/")
    void getData(Callback<Chapter[]> pResponse);
}
