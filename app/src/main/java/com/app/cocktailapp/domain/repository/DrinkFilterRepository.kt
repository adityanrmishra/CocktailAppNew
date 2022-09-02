package com.app.cocktailapp.domain.repository

import com.app.cocktailapp.data.model.DrinkFilter

interface DrinkFilterRepository {
    suspend fun getDrinkFilter(): DrinkFilter
}
