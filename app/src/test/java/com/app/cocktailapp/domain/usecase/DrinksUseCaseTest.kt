package com.app.cocktailapp.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.cocktailapp.common.TestCoroutineRule
import com.app.cocktailapp.data.MockResp
import com.app.cocktailapp.data.errorMessage
import com.app.cocktailapp.data.idDrink
import com.app.cocktailapp.data.strCategory
import com.app.cocktailapp.domain.repository.DrinkRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DrinksUseCaseTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val drinksRepositoryImp = mockk<DrinkRepository>()
    lateinit var drinksUseCase: DrinksUseCase

    @Before
    fun setUp() {
        drinksUseCase = DrinksUseCase(drinksRepositoryImp)
    }

    @Test
    fun `Given response data when fetch fetchDrinksByCategory from DrinksUseCaseImp expect result has data`() = runTest {
        coEvery { drinksUseCase.fetchDrinksByCategory(strCategory) } returns MockResp.getDrinksResourceData()

        val data = MockResp.getDrinksListData()

        Assert.assertEquals(data.drinksResponseModel.first().idDrink, idDrink)
    }

    @Test
    fun `Given http error when fetch fetch fetchDrinksByCategory from DrinksUseCaseImp expect null data`() = runTest {
        coEvery { drinksUseCase.fetchDrinksByCategory(strCategory) } returns MockResp.getDrinksFailureMock()

        val data = MockResp.getDrinksFailureMock().first()

        Assert.assertEquals(errorMessage, data.message)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}