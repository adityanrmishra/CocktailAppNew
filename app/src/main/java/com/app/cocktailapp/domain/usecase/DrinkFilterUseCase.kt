package com.app.cocktailapp.domain.usecase

import com.app.cocktailapp.core.NetworkConfig.UNKNOWN_ERROR
import com.app.cocktailapp.core.Resource
import com.app.cocktailapp.domain.errorhandler.ErrorHandler
import com.app.cocktailapp.domain.mapper.DrinkFilterMapper
import com.app.cocktailapp.domain.model.FilterModel
import com.app.cocktailapp.domain.repository.DrinkFilterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DrinkFilterUseCase @Inject constructor(
    private val repository: DrinkFilterRepository,
    private val mapper: DrinkFilterMapper,
    private val errorHandler: ErrorHandler
) {

    operator fun invoke(): Flow<Resource<List<FilterModel>>> = flow {

        emit(Resource.Loading())

        val data =
            repository.getDrinkFilter()

        val domainData =
            if (data.drinks.isNotEmpty()) data.drinks
                .map { mapper.mapToOut(it) }
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