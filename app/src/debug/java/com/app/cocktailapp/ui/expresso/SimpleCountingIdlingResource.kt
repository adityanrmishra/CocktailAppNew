package com.app.cocktailapp.ui.expresso

import androidx.test.espresso.IdlingResource
import kotlin.jvm.Volatile
import androidx.test.espresso.IdlingResource.ResourceCallback
import com.google.gson.internal.`$Gson$Preconditions`
import java.util.concurrent.atomic.AtomicInteger

class SimpleCountingIdlingResource(resourceName: String) : IdlingResource {
    private val mResourceName: String
    private val counter = AtomicInteger(0)

    @Volatile
    private var resourceCallback: ResourceCallback? = null
    override fun getName(): String {
        return mResourceName
    }

    override fun isIdleNow(): Boolean {
        return counter.get() == 0
    }

    override fun registerIdleTransitionCallback(resourceCallback: ResourceCallback) {
        this.resourceCallback = resourceCallback
    }

    fun increment() {
        counter.getAndIncrement()
    }

    fun decrement() {
        val counterVal = counter.decrementAndGet()
        if (counterVal == 0) {
            // we've gone from non-zero to zero. That means we're idle now! Tell espresso.
            if (null != resourceCallback) {
                resourceCallback!!.onTransitionToIdle()
            }
        }
        require(counterVal >= 0) { "Counter has been corrupted!" }
    }

    init {
        mResourceName = `$Gson$Preconditions`.checkNotNull(resourceName)
    }
}