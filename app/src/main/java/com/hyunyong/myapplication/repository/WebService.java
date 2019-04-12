package com.hyunyong.myapplication.repository;

import com.hyunyong.myapplication.data.Recipe;

import retrofit2.Call;
import retrofit2.http.GET;

interface WebService {

    @GET("2017/May/59121517_baking/baking.json")
    public Call<Recipe> getBakingData();
}
