package com.app.cocktailapp.core

import com.app.cocktailapp.data.model.DrinkFilter
import com.app.cocktailapp.data.model.DrinkResponseModel
import com.app.cocktailapp.data.model.Drinks
import com.app.cocktailapp.data.model.FilterResponseModel
import com.app.cocktailapp.domain.mapper.DrinkFilterMapper
import com.app.cocktailapp.domain.mapper.DrinkModelMapper
import com.app.cocktailapp.domain.model.DrinkModel
import com.app.cocktailapp.domain.model.FilterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow


const val idDrink = "14598"
const val strCategory = "Cocktail"

object MockResponse {

    fun getFilterData(): DrinkFilter {
        val filterResponseModel = FilterResponseModel(
            strCategory = "Cocktail"
        )
        return DrinkFilter(arrayListOf(filterResponseModel))
    }

    fun getDrinksData(): Drinks {
        val drinkModel = DrinkResponseModel(
            idDrink = "14598",
            strDrink = "50",
            strTags = null,
            strCategory = "Ordinary Drink",
            strIBA = null,
            strAlcoholic = "Alcoholic",
            strGlass = "Collins Glass",
            strInstructions = "fill glass with crushed ice. Add vodka. Add a splash of grand-marnier. Fill with o.j.",
            strDrinkThumb = "https://www.thecocktaildb.com/images/media/drink/wwpyvr1461919316.jpg",
            strIngredient1 = "Vanilla vodka",
            strMeasure1 = "2 1/2 oz ",
            dateModified = "2016-04-29 09:41:56"
        )
        return Drinks(arrayListOf(drinkModel))
    }

    fun getDrinkData(): DrinkResponseModel {
        return DrinkResponseModel(
            idDrink = "14598",
            strDrink = "50",
            strTags = null,
            strCategory = "Ordinary Drink",
            strIBA = null,
            strAlcoholic = "Alcoholic",
            strGlass = "Collins Glass",
            strInstructions = "fill glass with crushed ice. Add vodka. Add a splash of grand-marnier. Fill with o.j.",
            strDrinkThumb = "https://www.thecocktaildb.com/images/media/drink/wwpyvr1461919316.jpg",
            strIngredient1 = "Vanilla vodka",
            strMeasure1 = "2 1/2 oz ",
            dateModified = "2016-04-29 09:41:56"
        )
    }

    fun getFilterResourceData(): Flow<Resource<List<FilterModel>>> = channelFlow {
        var domainFilterData = listOf<FilterModel>()
        val mapper = DrinkFilterMapper()
        val data =
            getFilterData()
        domainFilterData =
            if (data.drinks.isNotEmpty()) data.drinks.map { mapper.mapToOut(it) } else domainFilterData
        send(Resource.Success(data = domainFilterData))

    }

    fun getDrinksResourceData(): Flow<Resource<List<DrinkModel>>> = channelFlow {
        var domainDrinksData = listOf<DrinkModel>()
        val mapper = DrinkModelMapper()
        val data =
            getDrinksData()
        domainDrinksData =
            if (data.drinkResponseModels.isNotEmpty()) data.drinkResponseModels.map {
                mapper.mapToOut(
                    it
                )
            } else domainDrinksData
        send(Resource.Success(data = domainDrinksData))

    }

    fun getDrinkResourceData(): Flow<Resource<List<DrinkModel>>> = channelFlow {
        var domainDrinksData = listOf<DrinkModel>()
        val mapper = DrinkModelMapper()
        val data =
            getDrinksData()
        domainDrinksData =
            if (data.drinkResponseModels.isNotEmpty()) data.drinkResponseModels.map {
                mapper.mapToOut(
                    it
                )
            } else domainDrinksData
        send(Resource.Success(data = domainDrinksData))

    }

    fun getFilterFailureMock(): Flow<Resource<List<FilterModel>>> = channelFlow {
        val domainData = listOf<FilterModel>()
        send(
            Resource.Error(
                message = "An Unknown error occurred",
                data = domainData,
                errorEntity = ErrorEntity.Network
            )
        )
    }

    fun getDrinksFailureMock(): Flow<Resource<List<DrinkModel>>> = channelFlow {
        val domainData = listOf<DrinkModel>()
        send(
            Resource.Error(
                message = "An Unknown error occurred",
                data = domainData,
                errorEntity = ErrorEntity.Network
            )
        )
    }

    fun getDrinkFailureMock(): Flow<Resource<List<DrinkModel>>> = channelFlow {
        val domainData = listOf<DrinkModel>()
        send(
            Resource.Error(
                message = "An Unknown error occurred",
                data = domainData,
                errorEntity = ErrorEntity.Network
            )
        )
    }
}