package com.app.cocktailapp.data.repository

import com.app.cocktailapp.data.api.ApiService
import com.app.cocktailapp.data.model.Drinks
import com.app.cocktailapp.domain.repository.DrinkRepository

class DrinkRepositoryImpl (private val apiService: ApiService) :
    DrinkRepository {

    override suspend fun getDrinkById(id: String): Drinks {
        return apiService.getDrinkById(id)
    }
}