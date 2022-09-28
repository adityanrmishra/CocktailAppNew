package com.app.cocktailapp.data.repository

import com.app.cocktailapp.common.ErrorHandler
import com.app.cocktailapp.common.Resource
import com.app.cocktailapp.data.api.ApiService
import com.app.cocktailapp.data.mappers.DrinksMapper
import com.app.cocktailapp.data.network.NetworkConfig
import com.app.cocktailapp.domain.repository.DrinksRepositoryImp
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DrinksRepository @Inject constructor(
    private val apiService: ApiService,
    private val drinksMapper: DrinksMapper,
    private val errorHandler: ErrorHandler
): DrinksRepositoryImp {

    override fun fetchDrinksByCategory(filter: String) = flow {
        emit(Resource.Loading())

        val data =
            apiService.fetchDrinksByCategory(filter)

        val domainData =
            if (data.drinksResponseModel.isNotEmpty()) data.drinksResponseModel
                .map { drinksMapper.mapToOut(it) }
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