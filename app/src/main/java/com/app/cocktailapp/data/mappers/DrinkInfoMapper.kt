package com.app.cocktailapp.data.mappers

import com.app.cocktailapp.data.model.DrinkResponseModel
import com.app.cocktailapp.domain.model.DrinkModel
import javax.inject.Inject

class DrinkInfoMapper @Inject constructor() : Mapper<DrinkModel, DrinkResponseModel> {
    override fun mapToOut(input: DrinkResponseModel): DrinkModel {
        return DrinkModel(
            idDrink = input.idDrink,
            strDrink = input.strDrink,
            strCategory = input.strCategory,
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