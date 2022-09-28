package com.app.cocktailapp.ui.mapper

import com.app.cocktailapp.domain.model.DrinkModel
import com.app.cocktailapp.ui.model.DrinkInfo
import javax.inject.Inject

class DrinkMapperUI @Inject constructor() : Mapper<DrinkInfo, DrinkModel> {
    override fun mapToOut(input: DrinkModel): DrinkInfo {
        return DrinkInfo(
            input.idDrink,
            input.strDrink,
            input.strCategory,
            input.strAlcoholic,
            input.strGlass,
            input.strInstructions,
            input.strDrinkThumb,
            input.strIngredient1,
            input.strMeasure1,
            input.dateModified
        )
    }
}