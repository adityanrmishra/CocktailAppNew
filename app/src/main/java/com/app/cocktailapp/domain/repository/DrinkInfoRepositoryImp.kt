package com.app.cocktailapp.domain.repository

import com.app.cocktailapp.common.Resource
import com.app.cocktailapp.domain.model.DrinkModel
import kotlinx.coroutines.flow.Flow


interface DrinkInfoRepositoryImp {
    fun getDrinkById(id: String): Flow<Resource<List<DrinkModel>>>
}