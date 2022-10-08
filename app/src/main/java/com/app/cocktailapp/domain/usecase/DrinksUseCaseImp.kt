package com.app.cocktailapp.domain.usecase

import com.app.cocktailapp.domain.model.Resource
import com.app.cocktailapp.domain.model.DrinksModel
import com.app.cocktailapp.domain.repository.DrinkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DrinksUseCaseImp @Inject constructor(private val drinkRepository: DrinkRepository) {
    fun fetchDrinksByCategory(filter: String): Flow<Resource<List<DrinksModel>>> {
        return drinkRepository.fetchDrinksByCategory(filter)
    }
}