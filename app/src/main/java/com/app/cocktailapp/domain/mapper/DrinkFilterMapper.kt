package com.app.cocktailapp.domain.mapper

import com.app.cocktailapp.core.Mapper
import com.app.cocktailapp.data.model.FilterResponseModel
import com.app.cocktailapp.domain.model.FilterModel
import javax.inject.Inject

class DrinkFilterMapper @Inject constructor() : Mapper<FilterModel, FilterResponseModel> {
    override fun mapToOut(input: FilterResponseModel): FilterModel {
        return FilterModel(
            strCategory = input.strCategory
        )
    }
}