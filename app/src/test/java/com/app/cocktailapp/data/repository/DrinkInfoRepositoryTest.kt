package com.app.cocktailapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.cocktailapp.data.MockResp
import com.app.cocktailapp.data.MockResp.getDrinkInfoFailureMock
import com.app.cocktailapp.data.MockResp.getDrinkInfoResourceData
import com.app.cocktailapp.data.errorMessage
import com.app.cocktailapp.data.idDrink
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class DrinkInfoRepositoryTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private var drinkInfoRepository = mockk<DrinkRepositoryImpl>()

    @Before
    fun setUp() {
    }

    @Test
    fun `Given response when fetch drink detail from drink info repository expect response contains idDrink`() = runTest {
        coEvery { drinkInfoRepository.getDrinkById(idDrink) } returns getDrinkInfoResourceData()

        val data = MockResp.getDrinkInfoData()

        Assert.assertEquals(data.drinkResponseModels.first().idDrink, idDrink)
    }

    @Test
    fun `Given error when fetch drink detail from drink info repository expect null data`() = runTest {
        coEvery { drinkInfoRepository.getDrinkById(idDrink) } returns getDrinkInfoFailureMock()

        val data = getDrinkInfoFailureMock().first()

        Assert.assertEquals(errorMessage, data.message)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}