package com.app.cocktailapp.usecase

import com.app.cocktailapp.core.MockResponse
import com.app.cocktailapp.core.TestCoroutineRule
import com.app.cocktailapp.core.strCategory
import com.app.cocktailapp.data.repository.DrinksRepositoryImpl
import com.app.cocktailapp.domain.errorhandler.GeneralErrorHandlerImpl
import com.app.cocktailapp.domain.mapper.DrinkModelMapper
import com.app.cocktailapp.domain.usecase.DrinksUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DrinksUseCaseTest {
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val drinksRepositoryImpl = mockk<DrinksRepositoryImpl>(relaxed = true)

    private val drinksUseCase by lazy {
        DrinksUseCase(
            drinksRepositoryImpl,
            DrinkModelMapper(),
            GeneralErrorHandlerImpl()
        )
    }

    @Test
    fun `Given response data when invoke drinks use case expect result has data`() = runTest {
        coEvery { drinksRepositoryImpl.fetchDrinksByCategory(strCategory) } returns MockResponse.getDrinksListData()
        val first = drinksUseCase.invoke(strCategory).drop(1).first()
        Assert.assertEquals(first.data?.get(0)?.strCategory, strCategory)
    }

    @Test
    fun `Given http error when invoke drinks use case expect null data`() = runTest {
        coEvery { drinksRepositoryImpl.fetchDrinksByCategory(strCategory) }.throws(Throwable("Test"))
        val first = drinksUseCase.invoke(strCategory).drop(1).first()
        Assert.assertNull(first.data)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

}