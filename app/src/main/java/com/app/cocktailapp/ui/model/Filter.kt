package com.app.cocktailapp.ui.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
class Filter (
    var strCategory : String? = null
): Parcelable