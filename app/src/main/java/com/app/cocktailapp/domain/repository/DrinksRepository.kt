package com.app.cocktailapp.domain.repository

import com.app.cocktailapp.data.model.Drinks

interface DrinksRepository {
    suspend fun fetchDrinksByCategory(filter: String): Drinks
}