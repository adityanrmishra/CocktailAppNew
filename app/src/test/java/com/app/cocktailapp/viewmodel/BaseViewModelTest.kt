package com.app.cocktailapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.cocktailapp.core.TestCoroutineRule
import io.mockk.runs
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.After
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runners.MethodSorters

@ExperimentalCoroutinesApi
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
abstract class BaseViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()
}
