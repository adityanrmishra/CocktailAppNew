package com.app.cocktailapp.usecase

import com.app.cocktailapp.core.MockResponse.getFilterData
import com.app.cocktailapp.core.TestCoroutineRule
import com.app.cocktailapp.data.repository.DrinkFilterRepositoryImpl
import com.app.cocktailapp.domain.errorhandler.GeneralErrorHandlerImpl
import com.app.cocktailapp.domain.mapper.DrinkFilterMapper
import com.app.cocktailapp.domain.usecase.DrinkFilterUseCase
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DrinkFilterUseCaseTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val drinkFilterRepositoryImpl = mockk<DrinkFilterRepositoryImpl>(relaxed = true)

    private val filterUseCase by lazy {
        DrinkFilterUseCase(
            drinkFilterRepositoryImpl,
            DrinkFilterMapper(),
            GeneralErrorHandlerImpl()
        )
    }

    @Test
    fun `Given response data when invoke drink filter use case expect result has data`() = runTest {
        coEvery { drinkFilterRepositoryImpl.getDrinkFilter() } returns getFilterData()

        val first = filterUseCase.invoke().drop(1).first()

        assertEquals("Cocktail", first.data?.get(0)?.strCategory)
    }

    @Test
    fun `Given http error when invoke drink filter use case expect null data`() = runTest {
        coEvery { drinkFilterRepositoryImpl.getDrinkFilter() }.throws(Throwable("Test"))

        val first = filterUseCase.invoke().drop(1).first()

        Assert.assertNull(first.data)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}