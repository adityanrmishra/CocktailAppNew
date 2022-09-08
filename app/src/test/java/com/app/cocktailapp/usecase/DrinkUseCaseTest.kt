package com.app.cocktailapp.usecase


import com.app.cocktailapp.core.MockResponse.getDrinkInfoData
import com.app.cocktailapp.core.TestCoroutineRule
import com.app.cocktailapp.core.idDrink
import com.app.cocktailapp.data.repository.DrinkRepositoryImpl
import com.app.cocktailapp.domain.errorhandler.GeneralErrorHandlerImpl
import com.app.cocktailapp.domain.mapper.DrinkModelMapper
import com.app.cocktailapp.domain.usecase.DrinkUseCase
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
class DrinkUseCaseTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val drinkRepositoryImpl = mockk<DrinkRepositoryImpl>()
    private val drinkUseCase by lazy {
        DrinkUseCase(
            drinkRepositoryImpl,
            DrinkModelMapper(),
            GeneralErrorHandlerImpl()
        )
    }

    @Test
    fun `Given response when invoke drink details use case expect response contains idDrink`() =
        runTest {
            coEvery { drinkRepositoryImpl.getDrinkById(idDrink) } returns getDrinkInfoData()
            val first = drinkUseCase.invoke(idDrink).drop(1).first()
            Assert.assertEquals(first.data?.get(0)?.idDrink, idDrink)
        }

    @Test
    fun `Given error when invoke drink details use case expect null data`() = runTest {
        coEvery { drinkRepositoryImpl.getDrinkById(idDrink) }.throws(Throwable())
        val first = drinkUseCase.invoke(idDrink).drop(1).first()
        Assert.assertNull(first.data)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }
}