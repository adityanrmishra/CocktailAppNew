package com.app.cocktailapp.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class FilterResponseModel (
    @SerializedName("strCategory" ) var strCategory : String? = null
) : Parcelable