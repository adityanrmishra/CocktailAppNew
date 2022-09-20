package com.app.cocktailapp.data.repository

import com.app.cocktailapp.common.Resource
import com.app.cocktailapp.common.ErrorHandler
import com.app.cocktailapp.data.api.ApiService
import com.app.cocktailapp.data.mappers.DrinkInfoMapper
import com.app.cocktailapp.data.network.NetworkConfig
import com.app.cocktailapp.domain.model.DrinkModel
import com.app.cocktailapp.domain.repository.DrinkInfoRepositoryImp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DrinkInfoRepository @Inject constructor(
    private val apiService: ApiService,
    private val drinkInfoMapper: DrinkInfoMapper,
    private val errorHandler: ErrorHandler
) : DrinkInfoRepositoryImp {
    override suspend fun getDrinkById(id: String): Flow<Resource<List<DrinkModel>>> = flow {
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