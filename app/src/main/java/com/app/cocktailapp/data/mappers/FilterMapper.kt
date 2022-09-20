package com.app.cocktailapp.data.mappers

import com.app.cocktailapp.data.model.FilterResponseModel
import com.app.cocktailapp.domain.model.FilterModel
import javax.inject.Inject

class FilterMapper @Inject constructor() : Mapper<FilterModel, FilterResponseModel> {
    override fun mapToOut(input: FilterResponseModel): FilterModel {
        return FilterModel(
            strCategory = input.strCategory
        )
    }
}