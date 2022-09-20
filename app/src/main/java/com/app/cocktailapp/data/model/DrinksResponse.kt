package com.app.cocktailapp.data.model

import com.google.gson.annotations.SerializedName

data class DrinksResponse(
    @SerializedName("drinks") val drinksResponseModel: ArrayList<DrinksResponseModel> = arrayListOf()
)