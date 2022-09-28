package com.app.cocktailapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.cocktailapp.data.mappers.DrinkInfoMapper
import com.app.cocktailapp.data.mappers.DrinksMapper
import com.app.cocktailapp.data.mappers.FilterMapper
import com.app.cocktailapp.domain.model.DrinkModel
import com.app.cocktailapp.domain.model.DrinksModel
import com.app.cocktailapp.domain.model.FilterModel
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapperDataTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        
    }
   
    @Test
    fun `map  FilterResponseModel of data to FilterModel of domain should return converted FilterModel`() = runTest {
        var domainFilterData = listOf<FilterModel>()
        val mapper = FilterMapper()
        val data = MockResp.getFilterData()
        domainFilterData =
            if (data.drinks.isNotEmpty()) data.drinks.map { mapper.mapToOut(it) } else domainFilterData
        Assert.assertEquals(strCategory, domainFilterData[0].strCategory)
    }
    
    @Test
    fun `map  DrinksResponseModel of data to DrinksModel of domain should return converted DrinksModel`() = runTest {
        var domainDrinksData = listOf<DrinksModel>()
        val mapper = DrinksMapper()
        val data = MockResp.getDrinksListData()
        domainDrinksData =
            if (data.drinksResponseModel.isNotEmpty()) data.drinksResponseModel.map {
                mapper.mapToOut(
                    it
                )
            } else domainDrinksData
        
        Assert.assertEquals(idDrink, domainDrinksData[0].idDrink)
    }

    @Test
    fun `map  DrinkResponseModel of data to DrinkModel of domain should return converted DrinkModel`() = runTest {
        var domainDrinksData = listOf<DrinkModel>()
        val mapper = DrinkInfoMapper()
        val data = MockResp.getDrinkInfoData()
        domainDrinksData =
            if (data.drinkResponseModels.isNotEmpty()) data.drinkResponseModels.map {
                mapper.mapToOut(
                    it
                )
            } else domainDrinksData

        Assert.assertEquals(idDrink, domainDrinksData[0].idDrink)
    }
    

    @After
    fun tearDown() {
        unmockkAll()
    }
}