package com.app.cocktailapp.domain.usecase

import com.app.cocktailapp.domain.model.Resource
import com.app.cocktailapp.domain.model.FilterModel
import com.app.cocktailapp.domain.repository.DrinkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilterUseCase @Inject constructor(private val drinkRepository: DrinkRepository) {
    fun getFilters(): Flow<Resource<List<FilterModel>>> {
        return drinkRepository.getFilters()
    }
}