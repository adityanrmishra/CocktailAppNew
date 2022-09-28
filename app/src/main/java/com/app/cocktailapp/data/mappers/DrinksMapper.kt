package com.app.cocktailapp.data.mappers

import com.app.cocktailapp.data.model.DrinksResponseModel
import com.app.cocktailapp.domain.model.DrinksModel
import javax.inject.Inject

class DrinksMapper @Inject constructor() : Mapper<DrinksModel, DrinksResponseModel> {
    override fun mapToOut(input: DrinksResponseModel): DrinksModel {
        return DrinksModel(
            idDrink = input.idDrink,
            strDrink = input.strDrink,
            strDrinkThumb = input.strDrinkThumb
        )
    }
}