package com.app.cocktailapp.domain.repository

import com.app.cocktailapp.data.model.Drinks

interface DrinkRepository {
    suspend fun getDrinkById(id: String): Drinks
}