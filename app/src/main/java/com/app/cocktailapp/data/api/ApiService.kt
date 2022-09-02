package com.app.cocktailapp.data.api

import com.app.cocktailapp.core.NetworkConfig
import com.app.cocktailapp.data.model.DrinkFilter
import com.app.cocktailapp.data.model.Drinks
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(NetworkConfig.DRINKS_FILTER)
    suspend fun getDrinkFilter(): DrinkFilter

    @GET(NetworkConfig.DRINKS_CATEGORY_WISE)
    suspend fun fetchDrinksByCategory(@Query("c") category: String): Drinks

    @GET(NetworkConfig.DRINKS_BY_ID)
    suspend fun getDrinkById(@Query("i") id: String): Drinks

}