package com.app.cocktailapp.domain.usecase

import com.app.cocktailapp.common.Resource
import com.app.cocktailapp.domain.model.DrinkModel
import com.app.cocktailapp.domain.repository.DrinkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DrinkInfoUseCaseImp @Inject constructor(private val drinkRepository: DrinkRepository) {
    fun getDrinkById(id: String): Flow<Resource<List<DrinkModel>>> {
        return drinkRepository.getDrinkById(id)
    }
}