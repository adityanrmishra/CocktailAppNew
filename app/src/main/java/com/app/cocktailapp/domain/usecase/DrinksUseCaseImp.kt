package com.app.cocktailapp.domain.usecase

import com.app.cocktailapp.common.Resource
import com.app.cocktailapp.domain.model.DrinksModel
import com.app.cocktailapp.domain.repository.DrinksRepositoryImp
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DrinksUseCaseImp @Inject constructor(private val drinksRepositoryImp: DrinksRepositoryImp) {
    fun fetchDrinksByCategory(filter: String): Flow<Resource<List<DrinksModel>>> {
        return drinksRepositoryImp.fetchDrinksByCategory(filter)
    }
}