package com.app.cocktailapp.domain.repository

import com.app.cocktailapp.domain.model.Resource
import com.app.cocktailapp.domain.model.DrinkModel
import com.app.cocktailapp.domain.model.DrinksModel
import com.app.cocktailapp.domain.model.FilterModel
import kotlinx.coroutines.flow.Flow

interface DrinkRepository {
    fun getFilters(): Flow<Resource<List<FilterModel>>>
    fun fetchDrinksByCategory(filter: String): Flow<Resource<List<DrinksModel>>>
    fun getDrinkById(id: String): Flow<Resource<List<DrinkModel>>>
}