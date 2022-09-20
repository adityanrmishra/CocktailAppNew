package com.app.cocktailapp.data.repository

import com.app.cocktailapp.common.Resource
import com.app.cocktailapp.common.ErrorHandler
import com.app.cocktailapp.data.api.ApiService
import com.app.cocktailapp.data.mappers.FilterMapper
import com.app.cocktailapp.data.network.NetworkConfig
import com.app.cocktailapp.domain.model.FilterModel
import com.app.cocktailapp.domain.repository.FilterRepositoryImp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FilterRepository @Inject constructor(
    private val apiService: ApiService,
    private val filterMapper: FilterMapper,
    private val errorHandler: ErrorHandler
): FilterRepositoryImp {

    override suspend fun getFilters(): Flow<Resource<List<FilterModel>>> = flow {
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

}