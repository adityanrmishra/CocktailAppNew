package com.app.cocktailapp.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.cocktailapp.common.TestCoroutineRule
import com.app.cocktailapp.data.MockResp
import com.app.cocktailapp.data.errorMessage
import com.app.cocktailapp.data.idDrink
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
class DrinkInfoUseCaseImpTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val drinkInfoRepositoryImp = mockk<DrinkRepository>()
    private lateinit var drinkInfoUseCaseImp: DrinkInfoUseCase

    @Before
    fun setUp() {
        drinkInfoUseCaseImp = DrinkInfoUseCase(drinkInfoRepositoryImp)
    }


    @Test
    fun `Given response when fetch drink detail from getDrinkById of DrinkInfoUseCaseImp expect response contains idDrink`() = runTest {
        coEvery { drinkInfoUseCaseImp.getDrinkById(idDrink) } returns MockResp.getDrinkInfoResourceData()

        val data = MockResp.getDrinkInfoData().drinkResponseModels.first()

        Assert.assertEquals(data.idDrink, idDrink)
    }

    @Test
    fun `Given error when fetch drink detail from getDrinkById of DrinkInfoUseCaseImp expect null data`() = runTest {
        coEvery { drinkInfoUseCaseImp.getDrinkById(idDrink) } returns MockResp.getDrinkInfoFailureMock()

        val data = MockResp.getDrinkInfoFailureMock().first()

        Assert.assertEquals(errorMessage, data.message)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

}