package com.app.cocktailapp.data.api

import com.app.cocktailapp.data.model.DrinkResponse
import com.app.cocktailapp.data.network.NetworkConfig
import com.app.cocktailapp.data.model.FilterResponse
import com.app.cocktailapp.data.model.DrinksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(NetworkConfig.DRINKS_FILTER)
    suspend fun getDrinkFilter(): FilterResponse

    @GET(NetworkConfig.DRINKS_CATEGORY_WISE)
    suspend fun fetchDrinksByCategory(@Query("c") category: String): DrinksResponse

    @GET(NetworkConfig.DRINKS_BY_ID)
    suspend fun getDrinkById(@Query("i") id: String): DrinkResponse

}