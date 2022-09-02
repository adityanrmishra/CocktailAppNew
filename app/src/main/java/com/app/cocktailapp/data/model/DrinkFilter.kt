package com.app.cocktailapp.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DrinkFilter (
    @SerializedName("drinks" ) var drinks : ArrayList<FilterResponseModel> = arrayListOf()
) : Parcelable