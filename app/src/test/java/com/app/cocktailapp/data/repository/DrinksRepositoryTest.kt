package com.app.cocktailapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.cocktailapp.data.MockResp.getDrinksFailureMock
import com.app.cocktailapp.data.MockResp.getDrinksListData
import com.app.cocktailapp.data.MockResp.getDrinksResourceData
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
class DrinksRepositoryTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private var drinksRepository = mockk<DrinkRepositoryImpl>()

    @Before
    fun setUp() {
    }

    @Test
    fun `Given response data when fetch drink by category of drinks repository expect result has data`() = runTest {
        coEvery { drinksRepository.fetchDrinksByCategory(strCategory) } returns getDrinksResourceData()

        val data = getDrinksListData()

        Assert.assertEquals(data.drinksResponseModel.first().idDrink, idDrink)
    }

    @Test
    fun `Given http error when fetch drink by category of drinks repository expect null data`() = runTest {
        coEvery { drinksRepository.fetchDrinksByCategory(strCategory) } returns getDrinksFailureMock()

        val data = getDrinksFailureMock().first()

        Assert.assertEquals(errorMessage, data.message)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}