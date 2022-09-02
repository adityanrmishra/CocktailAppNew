package com.app.cocktailapp.data.repository

import com.app.cocktailapp.data.api.ApiService
import com.app.cocktailapp.data.model.Drinks
import com.app.cocktailapp.domain.repository.DrinksRepository

class DrinksRepositoryImpl (private val apiService: ApiService) :
    DrinksRepository {

    override suspend fun fetchDrinksByCategory(filter: String): Drinks {
        return apiService.fetchDrinksByCategory(filter)
    }
}