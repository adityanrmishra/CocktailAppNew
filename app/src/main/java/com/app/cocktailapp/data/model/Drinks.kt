package com.app.cocktailapp.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Keep
@Parcelize
data class Drinks(
    @SerializedName("drinks")
    val drinkResponseModels: List<DrinkResponseModel>
) : Parcelable