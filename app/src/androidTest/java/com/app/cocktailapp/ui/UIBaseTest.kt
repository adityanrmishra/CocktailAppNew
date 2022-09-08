package com.app.cocktailapp.ui

import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.app.cocktailapp.ui.expresso.EspressoIdlingResource.getIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
open class UIBaseTest {
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(getIdlingResource())
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(getIdlingResource())
    }
}