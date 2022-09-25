package com.app.cocktailapp.ui

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.testing.FragmentScenario
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.app.cocktailapp.R
import com.app.cocktailapp.ui.detail.DrinkInfoFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class UIElementsTest : UIBaseTest() {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(MainActivity::class.java)

    lateinit var fragmentScenario: FragmentScenario<DrinkInfoFragment>
    lateinit var navController: TestNavHostController


    @Test
    fun test_chipClick() {
        val chip = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withText("Shake"),
                childAtPosition(
                    Matchers.allOf(
                        ViewMatchers.withId(R.id.drink_category),
                        childAtPosition(
                            ViewMatchers.withId(R.id.drink_category_tag),
                            0
                        )
                    ),
                    2
                )
            )
        )
        chip.perform(ViewActions.scrollTo(), ViewActions.click())
    }

    @Test
    fun test_recyclerViewVisibility() {
        val recyclerView = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.drink_recycler_view),
                childAtPosition(
                    ViewMatchers.withId(R.id.view_header),
                    2
                )
            )
        )
        recyclerView.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    @Test
    fun test_recyclerViewItemClick() {
        val recyclerView = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.drink_recycler_view),
                childAtPosition(
                    ViewMatchers.withId(R.id.view_header),
                    2
                )
            )
        )
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                3,
                ViewActions.click()
            )
        )
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