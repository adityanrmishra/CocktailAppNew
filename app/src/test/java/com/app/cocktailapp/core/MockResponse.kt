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
const val strCategory = "Ordinary Drink"

object MockResponse {

    fun getFilterData(): DrinkFilter {
        val filterResponseModel = FilterResponseModel(
            strCategory = "Ordinary Drink"
        )
        return DrinkFilter(arrayListOf(filterResponseModel))
    }

    fun getDrinkInfoData(): Drinks {
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

    fun getDrinksListData(): Drinks {
        val drinkResponseModel = DrinkResponseModel(
            idDrink="14598",
            strDrink="50",
            strDrinkAlternate="null",
            strTags=null,
            strVideo=null,
            strCategory="Ordinary Drink",
            strIBA=null,
            strAlcoholic="Alcoholic",
            strGlass="Collins Glass",
            strInstructions="fill glass with crushed ice. Add vodka. Add a splash of grand-marnier. Fill with o.j.",
            strInstructionsES=null,
            strInstructionsDE=null,
            strInstructionsFR=null,
            strInstructionsIT=null,
            strInstructionsZHHANS=null,
            strInstructionsZHHANT=null,
            strDrinkThumb="https://www.thecocktaildb.com/images/media/drink/wwpyvr1461919316.jpg",
            strIngredient1="Vanilla vodka",
            strIngredient2=null,
            strIngredient3=null,
            strIngredient4=null,
            strIngredient5=null,
            strIngredient6=null,
            strIngredient7=null,
            strIngredient8=null,
            strIngredient9=null,
            strIngredient10=null,
            strIngredient11=null,
            strIngredient12=null,
            strIngredient13=null,
            strIngredient14=null,
            strIngredient15=null,
            strMeasure1="2 1/2 oz ",
            strMeasure2=null,
            strMeasure3=null,
            strMeasure4=null,
            strMeasure5=null,
            strMeasure6=null,
            strMeasure7=null,
            strMeasure8=null,
            strMeasure9=null,
            strMeasure10=null,
            strMeasure11=null,
            strMeasure12=null,
            strMeasure13=null,
            strMeasure14=null,
            strMeasure15=null,
            strImageSource=null,
            strImageAttribution=null,
            strCreativeCommonsConfirmed=null,
            dateModified="2016-04-29 09:41:56"
        )
        return Drinks(arrayListOf(drinkResponseModel))
    }

    /*
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
    */

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
            getDrinksListData()
        domainDrinksData =
            if (data.drinkResponseModels.isNotEmpty()) data.drinkResponseModels.map {
                mapper.mapToOut(
                    it
                )
            } else domainDrinksData
        send(Resource.Success(data = domainDrinksData))

    }

    fun getDrinkInfoResourceData(): Flow<Resource<List<DrinkModel>>> = channelFlow {
        var domainDrinksData = listOf<DrinkModel>()
        val mapper = DrinkModelMapper()
        val data =
            getDrinkInfoData()
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

    fun getDrinkInfoFailureMock(): Flow<Resource<List<DrinkModel>>> = channelFlow {
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