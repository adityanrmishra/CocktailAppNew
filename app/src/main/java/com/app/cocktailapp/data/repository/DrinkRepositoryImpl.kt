package com.app.cocktailapp.data.repository

import com.app.cocktailapp.domain.model.Resource
import com.app.cocktailapp.data.api.ApiService
import com.app.cocktailapp.data.mappers.DrinkInfoMapper
import com.app.cocktailapp.data.mappers.DrinksMapper
import com.app.cocktailapp.data.mappers.FilterMapper
import com.app.cocktailapp.data.network.NetworkConfig
import com.app.cocktailapp.domain.model.DrinkModel
import com.app.cocktailapp.domain.model.DrinksModel
import com.app.cocktailapp.domain.model.FilterModel
import com.app.cocktailapp.domain.repository.DrinkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DrinkRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val drinksMapper: DrinksMapper,
    private val filterMapper: FilterMapper,
    private val drinkInfoMapper: DrinkInfoMapper
) : DrinkRepository {

    override fun getFilters(): Flow<Resource<List<FilterModel>>> = flow {
        try {
            val data = apiService.getDrinkFilter()
            val domainData =
                if (data.drinks.isNotEmpty()) data.drinks
                    .map { filterMapper.mapToOut(it) }
                else emptyList()
            emit(Resource.Success(domainData))
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: NetworkConfig.UNKNOWN_ERROR
                )
            )
        }
    }

    override fun fetchDrinksByCategory(filter: String): Flow<Resource<List<DrinksModel>>> = flow {
        try {
            val data = apiService.fetchDrinksByCategory(filter)
            val domainData =
                if (data.drinksResponseModel.isNotEmpty()) data.drinksResponseModel
                    .map { drinksMapper.mapToOut(it) }
                else emptyList()

            emit(Resource.Success(domainData))
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: NetworkConfig.UNKNOWN_ERROR
                )
            )
        }
    }

    override fun getDrinkById(id: String): Flow<Resource<List<DrinkModel>>> = flow {
        try {
            val data =
                apiService.getDrinkById(id)

            val domainData =
                if (data.drinkResponseModels.isNotEmpty()) data.drinkResponseModels
                    .map { drinkInfoMapper.mapToOut(it) }
                else emptyList()

            emit(Resource.Success(domainData))

        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.localizedMessage ?: NetworkConfig.UNKNOWN_ERROR
                )
            )
        }
    }

}