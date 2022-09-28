package com.app.cocktailapp.data.model

import com.google.gson.annotations.SerializedName

data class FilterResponse (
    @SerializedName("drinks" ) var drinks : ArrayList<FilterResponseModel> = arrayListOf()
)