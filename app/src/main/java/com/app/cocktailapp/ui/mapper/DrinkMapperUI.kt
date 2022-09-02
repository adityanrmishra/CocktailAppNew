package com.app.cocktailapp.ui.mapper

import com.app.cocktailapp.core.Mapper
import com.app.cocktailapp.domain.model.DrinkModel
import com.app.cocktailapp.ui.model.Drink
import javax.inject.Inject

class DrinkMapperUI @Inject constructor() : Mapper<Drink, DrinkModel> {
    override fun mapToOut(input: DrinkModel): Drink {
        return Drink(
            input.idDrink,
            input.strDrink,
            input.strTags,
            input.strCategory,
            input.strIBA,
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