package com.solitelab.footballmatchschedule.ui


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.pressImeActionButton
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.EditText
import com.solitelab.footballmatchschedule.R
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import com.solitelab.footballmatchschedule.EspressoIdlingResource
import com.solitelab.footballmatchschedule.Util.hasItemm
import org.junit.After
import org.junit.Before


@RunWith(AndroidJUnit4::class)
class SearchActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(SearchActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun testItemFound() {
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("Barcelona"), pressImeActionButton())
        onView(withId(R.id.no_result)).check(matches(not(isDisplayed())))
        onView(withId(R.id.match_list)).check(
            matches(
                hasItemm(
                    hasDescendant(
                        withText(
                            containsString("Barcelona")
                        )
                    )
                )
            )
        )
    }

    @Test
    fun testItemNotFound() {
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("Not Found Item"), pressImeActionButton())
        onView(withId(R.id.no_result)).check(matches(isDisplayed()))
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }
}