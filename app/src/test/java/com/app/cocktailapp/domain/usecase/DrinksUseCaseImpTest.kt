package com.app.cocktailapp.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.cocktailapp.common.TestCoroutineRule
import com.app.cocktailapp.data.MockResp
import com.app.cocktailapp.data.errorMessage
import com.app.cocktailapp.data.idDrink
import com.app.cocktailapp.data.strCategory
import com.app.cocktailapp.domain.repository.DrinkInfoRepositoryImp
import com.app.cocktailapp.domain.repository.DrinksRepositoryImp
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
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DrinksUseCaseImpTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val drinksRepositoryImp = mockk<DrinksRepositoryImp>(relaxed = true)
    lateinit var drinksUseCaseImp: DrinksUseCaseImp

    @Before
    fun setUp() {
        drinksUseCaseImp = DrinksUseCaseImp(drinksRepositoryImp)
    }

    @Test
    fun `Given response data when fetch fetchDrinksByCategory from DrinksUseCaseImp expect result has data`() = runTest {
        coEvery { drinksUseCaseImp.fetchDrinksByCategory(strCategory) } returns MockResp.getDrinksResourceData()
        val data = MockResp.getDrinksListData()
        Assert.assertEquals(data.drinksResponseModel[0].idDrink, idDrink)
    }

    @Test
    fun `Given http error when fetch fetch fetchDrinksByCategory from DrinksUseCaseImp expect null data`() = runTest {
        coEvery { drinksUseCaseImp.fetchDrinksByCategory(strCategory) } returns MockResp.getDrinksFailureMock()
        val data = MockResp.getDrinksFailureMock().first()
        Assert.assertEquals(errorMessage, data.message)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}