package com.app.cocktailapp.ui.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
class Drink(
    var idDrink: String? = null,
    var strDrink: String? = null,
    var strTags: String? = null,
    var strCategory: String? = null,
    var strIBA: String? = null,
    var strAlcoholic: String? = null,
    var strGlass: String? = null,
    var strInstructions: String? = null,
    var strDrinkThumb: String? = null,
    var strIngredient1: String? = null,
    var strMeasure1: String? = null,
    var dateModified: String? = null
): Parcelable