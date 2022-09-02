package com.app.cocktailapp.domain.usecase

import com.app.cocktailapp.core.NetworkConfig.UNKNOWN_ERROR
import com.app.cocktailapp.core.Resource
import com.app.cocktailapp.domain.errorhandler.ErrorHandler
import com.app.cocktailapp.domain.mapper.DrinkModelMapper
import com.app.cocktailapp.domain.model.DrinkModel
import com.app.cocktailapp.domain.repository.DrinksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DrinksUseCase @Inject constructor(
    private val drinksRepository: DrinksRepository,
    private val drinkModelMapper: DrinkModelMapper,
    private val errorHandler: ErrorHandler
) {
    operator fun invoke(filter: String): Flow<Resource<List<DrinkModel>>> = flow {
        emit(Resource.Loading())

        val data =
            drinksRepository.fetchDrinksByCategory(filter)

        val domainData =
            if (data.drinkResponseModels.isNotEmpty()) data.drinkResponseModels
                .map { drinkModelMapper.mapToOut(it) }
            else emptyList()

        emit(Resource.Success(data = domainData))

    }.catch { throwable ->
        emit(
            Resource.Error(
                message = throwable.localizedMessage ?: UNKNOWN_ERROR,
                errorEntity = errorHandler.getError(throwable)
            )
        )
    }
}