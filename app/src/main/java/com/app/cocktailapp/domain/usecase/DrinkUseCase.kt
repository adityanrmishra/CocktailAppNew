package com.app.cocktailapp.domain.usecase

import com.app.cocktailapp.core.NetworkConfig
import com.app.cocktailapp.core.Resource
import com.app.cocktailapp.domain.errorhandler.ErrorHandler
import com.app.cocktailapp.domain.mapper.DrinkModelMapper
import com.app.cocktailapp.domain.model.DrinkModel
import com.app.cocktailapp.domain.repository.DrinkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DrinkUseCase @Inject constructor(
    private val drinkRepository: DrinkRepository,
    private val drinkModelMapper: DrinkModelMapper,
    private val errorHandler: ErrorHandler
) {
    operator fun invoke(id: String): Flow<Resource<List<DrinkModel>>> = flow {
        emit(Resource.Loading())

        val data =
            drinkRepository.getDrinkById(id)

        val domainData =
            if (data.drinkResponseModels.isNotEmpty()) data.drinkResponseModels
                .map { drinkModelMapper.mapToOut(it) }
            else emptyList()

        emit(Resource.Success(data = domainData))

    }.catch { throwable ->
        emit(
            Resource.Error(
                message = throwable.localizedMessage ?: NetworkConfig.UNKNOWN_ERROR,
                errorEntity = errorHandler.getError(throwable)
            )
        )
    }
}