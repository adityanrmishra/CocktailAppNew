package com.app.cocktailapp.domain.repository

import com.app.cocktailapp.common.Resource
import com.app.cocktailapp.domain.model.DrinksModel
import kotlinx.coroutines.flow.Flow

interface DrinksRepositoryImp {
    suspend fun fetchDrinksByCategory(filter: String): Flow<Resource<List<DrinksModel>>>
}