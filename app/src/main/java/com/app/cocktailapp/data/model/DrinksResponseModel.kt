package com.app.cocktailapp.data.model

import com.google.gson.annotations.SerializedName

data class DrinksResponseModel(
    @SerializedName("idDrink") var idDrink: String,
    @SerializedName("strDrink") var strDrink: String,
    @SerializedName("strDrinkThumb") var strDrinkThumb: String
)
