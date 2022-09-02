package com.app.cocktailapp.data.repository

import com.app.cocktailapp.data.api.ApiService
import com.app.cocktailapp.data.model.DrinkFilter
import com.app.cocktailapp.domain.repository.DrinkFilterRepository

class DrinkFilterRepositoryImpl(private val apiService: ApiService) :
    DrinkFilterRepository {

    override suspend fun getDrinkFilter(): DrinkFilter {
        return apiService.getDrinkFilter()
    }
}