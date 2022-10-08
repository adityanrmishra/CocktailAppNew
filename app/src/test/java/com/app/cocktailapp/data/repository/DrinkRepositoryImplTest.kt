package com.app.cocktailapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.cocktailapp.data.MockResp
import com.app.cocktailapp.data.errorMessage
import com.app.cocktailapp.data.idDrink
import com.app.cocktailapp.data.strCategory
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class DrinkRepositoryImplTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private var drinkRepositoryImpl = mockk<DrinkRepositoryImpl>()

    @Before
    fun setUp() {
    }

    @Test
    fun `Given Filters When getFilterData returns Success`() = runTest {
        coEvery { drinkRepositoryImpl.getFilters() } returns MockResp.getFilterResourceData()

        val data = MockResp.getFilterData().drinks

        Assert.assertEquals("Ordinary Drink", data.first().strCategory)
    }

    @Test
    fun `Given Filters When getFilterData returns Error`() = runTest {
        coEvery { drinkRepositoryImpl.getFilters() } returns MockResp.getFilterFailureMock()

        val data = MockResp.getFilterFailureMock().first()

        Assert.assertEquals(errorMessage, data.message)
    }

    @Test
    fun `Given response data when fetch drink by category of drinks repository expect result has data`() = runTest {
        coEvery { drinkRepositoryImpl.fetchDrinksByCategory(strCategory) } returns MockResp.getDrinksResourceData()

        val data = MockResp.getDrinksListData()

        Assert.assertEquals(data.drinksResponseModel.first().idDrink, idDrink)
    }

    @Test
    fun `Given http error when fetch drink by category of drinks repository expect null data`() = runTest {
        coEvery { drinkRepositoryImpl.fetchDrinksByCategory(strCategory) } returns MockResp.getDrinksFailureMock()

        val data = MockResp.getDrinksFailureMock().first()

        Assert.assertEquals(errorMessage, data.message)
    }

    @Test
    fun `Given response when fetch drink detail from drink info repository expect response contains idDrink`() = runTest {
        coEvery { drinkRepositoryImpl.getDrinkById(idDrink) } returns MockResp.getDrinkInfoResourceData()

        val data = MockResp.getDrinkInfoData()

        Assert.assertEquals(data.drinkResponseModels.first().idDrink, idDrink)
    }

    @Test
    fun `Given error when fetch drink detail from drink info repository expect null data`() = runTest {
        coEvery { drinkRepositoryImpl.getDrinkById(idDrink) } returns MockResp.getDrinkInfoFailureMock()

        val data = MockResp.getDrinkInfoFailureMock().first()

        Assert.assertEquals(errorMessage, data.message)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}