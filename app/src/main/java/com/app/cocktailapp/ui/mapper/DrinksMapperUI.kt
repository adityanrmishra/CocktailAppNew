package com.app.cocktailapp.ui.mapper

import com.app.cocktailapp.domain.model.DrinksModel
import com.app.cocktailapp.ui.model.Drink
import javax.inject.Inject

class DrinksMapperUI @Inject constructor() : Mapper<Drink, DrinksModel> {
    override fun mapToOut(input: DrinksModel): Drink {
        return Drink(
            input.idDrink,
            input.strDrink,
            input.strDrinkThumb
        )
    }
}