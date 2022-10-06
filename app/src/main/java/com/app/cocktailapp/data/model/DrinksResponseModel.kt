package com.app.cocktailapp.data.model

import com.google.gson.annotations.SerializedName

data class DrinksResponseModel(
    @SerializedName("idDrink") val idDrink: String,
    @SerializedName("strDrink") val strDrink: String,
    @SerializedName("strDrinkThumb") val strDrinkThumb: String
)
