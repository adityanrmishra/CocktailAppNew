package com.app.cocktailapp.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.cocktailapp.common.ErrorHandler
import com.app.cocktailapp.data.MockResp.getFilterData
import com.app.cocktailapp.data.MockResp.getFilterFailureMock
import com.app.cocktailapp.data.MockResp.getFilterResourceData
import com.app.cocktailapp.data.api.ApiService
import com.app.cocktailapp.data.errorMessage
import com.app.cocktailapp.data.mappers.FilterMapper
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.*
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FilterRepositoryTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private var filterRepository = mockk<FilterRepository>(relaxed = true)

    @Before
    fun setUp() {
    }

    @Test
    fun `Given Filters When getFilterData returns Success`() = runTest {
        coEvery { filterRepository.getFilters() } returns getFilterResourceData()
        val data = getFilterData().drinks
        Assert.assertEquals("Ordinary Drink", data[0].strCategory)
    }

    @Test
    fun `Given Filters When getFilterData returns Error`() = runTest {
        coEvery { filterRepository.getFilters() } returns getFilterFailureMock()
        val data = getFilterFailureMock().first()
        Assert.assertEquals(errorMessage, data.message)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

}