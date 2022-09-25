package com.app.cocktailapp.ui


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.app.cocktailapp.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityThreadTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        Thread.sleep(5000)
        val recyclerView = onView(
            allOf(withId(R.id.drink_recycler_view),
                childAtPosition(
                    withId(R.id.view_header),
                    2)))
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        Thread.sleep(5000)
        val appCompatImageView = onView(
            allOf(withId(R.id.btn_back), withContentDescription("Back"),
                childAtPosition(
                    allOf(withId(R.id.view_header),
                        childAtPosition(
                            withId(R.id.nav_host_fragment_content_main),
                            0)),
                    0),
                isDisplayed()))
        appCompatImageView.perform(click())

        Thread.sleep(5000)
        val chip = onView(
            allOf(withText("Other/Unknown"),
                childAtPosition(
                    allOf(withId(R.id.drink_category),
                        childAtPosition(
                            withId(R.id.drink_category_tag),
                            0)),
                    3)))
        chip.perform(scrollTo(), click())

        Thread.sleep(5000)
        val recyclerView2 = onView(
            allOf(withId(R.id.drink_recycler_view),
                childAtPosition(
                    withId(R.id.view_header),
                    2)))
        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(2, click()))

        Thread.sleep(5000)
        val appCompatImageView2 = onView(
            allOf(withId(R.id.btn_back), withContentDescription("Back"),
                childAtPosition(
                    allOf(withId(R.id.view_header),
                        childAtPosition(
                            withId(R.id.nav_host_fragment_content_main),
                            0)),
                    0),
                isDisplayed()))
        appCompatImageView2.perform(click())

    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int,
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
