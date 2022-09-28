package com.app.cocktailapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.cocktailapp.data.MockResp
import com.app.cocktailapp.data.idDrink
import com.app.cocktailapp.data.strCategory
import com.app.cocktailapp.ui.mapper.DrinkMapperUI
import com.app.cocktailapp.ui.mapper.DrinksMapperUI
import com.app.cocktailapp.ui.mapper.FilterMapperUI
import com.app.cocktailapp.ui.model.Drink
import com.app.cocktailapp.ui.model.DrinkInfo
import com.app.cocktailapp.ui.model.Filter
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapperUITest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {

    }

    @Test
    fun `map  FilterModel of domain to Filter of ui should return converted Filter`() = runTest {
        var uiFilterData = listOf<Filter>()
        val mapper = FilterMapperUI()
        val data = MockResp.getFilterDataUI()
        uiFilterData =
            if (data.isNotEmpty()) data.map { mapper.mapToOut(it) } else uiFilterData
        Assert.assertEquals(strCategory, uiFilterData[0].strCategory)
    }

    @Test
    fun `map  DrinksModel of domain to Drink of ui should return converted Drink`() = runTest {
        var uiDrinkData = listOf<Drink>()
        val mapper = DrinksMapperUI()
        val data = MockResp.getDrinksListDataUI()

        uiDrinkData =
            if (data.isNotEmpty()) data.map {
                mapper.mapToOut(
                    it
                )
            } else uiDrinkData

        Assert.assertEquals(idDrink, uiDrinkData[0].idDrink)
    }

    @Test
    fun `map  DrinkModel of domain to DrinkInfo of ui should return converted DrinkInfo`() = runTest {
        var uiDrinkInfoData = listOf<DrinkInfo>()
        val mapper = DrinkMapperUI()
        val data = MockResp.getDrinkInfoDataUI()
        uiDrinkInfoData =
            if (data.isNotEmpty()) data.map {
                mapper.mapToOut(
                    it
                )
            } else uiDrinkInfoData

        Assert.assertEquals(idDrink, uiDrinkInfoData[0].idDrink)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}