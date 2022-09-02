package com.app.cocktailapp.domain.mapper

import com.app.cocktailapp.core.Mapper
import com.app.cocktailapp.data.model.DrinkResponseModel
import com.app.cocktailapp.domain.model.DrinkModel
import javax.inject.Inject

class DrinkModelMapper @Inject constructor() : Mapper<DrinkModel, DrinkResponseModel> {
    override fun mapToOut(input: DrinkResponseModel): DrinkModel {
        return DrinkModel(
            idDrink = input.idDrink,
            strDrink = input.strDrink,
            strTags = input.strTags,
            strCategory = input.strCategory,
            strIBA = input.strIBA,
            strAlcoholic = input.strAlcoholic,
            strGlass = input.strGlass,
            strInstructions = input.strInstructions,
            strDrinkThumb = input.strDrinkThumb,
            strIngredient1 = input.strIngredient1,
            strMeasure1 = input.strMeasure1,
            dateModified = input.dateModified
        )
    }
}