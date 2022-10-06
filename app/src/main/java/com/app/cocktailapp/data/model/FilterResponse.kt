package com.app.cocktailapp.data.model

import com.google.gson.annotations.SerializedName

data class FilterResponse (
    @SerializedName("drinks" ) val drinks : ArrayList<FilterResponseModel> = arrayListOf()
)