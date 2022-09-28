package com.app.cocktailapp.domain.usecase

import com.app.cocktailapp.common.Resource
import com.app.cocktailapp.domain.model.FilterModel
import com.app.cocktailapp.domain.repository.FilterRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilterUseCaseImp @Inject constructor(private val filterRepositoryImp: FilterRepositoryImp) {
    suspend fun getFilters(): Flow<Resource<List<FilterModel>>> {
        return filterRepositoryImp.getFilters()
    }
}