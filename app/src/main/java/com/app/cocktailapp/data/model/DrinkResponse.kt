package com.app.cocktailapp.data.model

import com.google.gson.annotations.SerializedName

data class DrinkResponse(
    @SerializedName("drinks") val drinkResponseModels: ArrayList<DrinkResponseModel> = arrayListOf()
)