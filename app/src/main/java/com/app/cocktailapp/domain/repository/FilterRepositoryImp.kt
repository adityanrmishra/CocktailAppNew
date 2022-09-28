package com.app.cocktailapp.domain.repository

import com.app.cocktailapp.common.Resource
import com.app.cocktailapp.domain.model.FilterModel
import kotlinx.coroutines.flow.Flow

interface FilterRepositoryImp {
    fun getFilters(): Flow<Resource<List<FilterModel>>>
}