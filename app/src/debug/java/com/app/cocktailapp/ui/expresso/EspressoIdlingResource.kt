package com.app.cocktailapp.ui.expresso

import androidx.test.espresso.IdlingResource

object EspressoIdlingResource {
    private const val RESOURCE = "GLOBAL"

    private val mCountingIdlingResource = SimpleCountingIdlingResource(RESOURCE)

    fun increment() {
        mCountingIdlingResource.increment()
    }

    fun decrement() {
        if (!mCountingIdlingResource.isIdleNow) {
            mCountingIdlingResource.decrement()
        }
    }

    fun getIdlingResource(): IdlingResource {
        return mCountingIdlingResource
    }

    inline fun <T> wrapEspressoIdlingResource(function: () -> T): T {
        EspressoIdlingResource.increment() // Set app as busy.
        return try {
            function()
        } finally {
            EspressoIdlingResource.decrement() // Set app as idle.
        }
    }

}