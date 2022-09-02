package com.app.cocktailapp.ui.mapper

import com.app.cocktailapp.core.Mapper
import com.app.cocktailapp.domain.model.FilterModel
import com.app.cocktailapp.ui.model.Filter
import javax.inject.Inject

class FilterMapperUI @Inject constructor() : Mapper<Filter, FilterModel> {
    override fun mapToOut(input: FilterModel): Filter {
        return Filter(
            input.strCategory
        )
    }
}