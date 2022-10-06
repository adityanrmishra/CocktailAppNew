package com.app.cocktailapp.data.repository

import com.app.cocktailapp.common.ErrorHandler
import com.app.cocktailapp.common.Resource
import com.app.cocktailapp.data.api.ApiService
import com.app.cocktailapp.data.mappers.DrinkInfoMapper
import com.app.cocktailapp.data.mappers.DrinksMapper
import com.app.cocktailapp.data.mappers.FilterMapper
import com.app.cocktailapp.data.network.NetworkConfig
import com.app.cocktailapp.domain.repository.DrinkRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DrinkRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val errorHandler: ErrorHandler,
    private val drinksMapper: DrinksMapper,
    private val filterMapper: FilterMapper,
    private val drinkInfoMapper: DrinkInfoMapper
) : DrinkRepository {

    override fun getFilters() = flow {
        emit(Resource.Loading())

        val data = apiService.getDrinkFilter()

        val domainData =
            if (data.drinks.isNotEmpty()) data.drinks
                .map { filterMapper.mapToOut(it) }
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

    override fun getDrinkById(id: String) = flow {
        emit(Resource.Loading())

        val data =
            apiService.getDrinkById(id)

        val domainData =
            if (data.drinkResponseModels.isNotEmpty()) data.drinkResponseModels
                .map { drinkInfoMapper.mapToOut(it) }
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