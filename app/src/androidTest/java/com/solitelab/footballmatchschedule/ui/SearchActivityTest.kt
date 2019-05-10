package com.solitelab.footballmatchschedule.ui


import android.support.test.espresso.Espresso.onView
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
import com.solitelab.footballmatchschedule.Util.hasItemm


@RunWith(AndroidJUnit4::class)
class SearchActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(SearchActivity::class.java)

    @Test
    fun testItemFound() {
        Thread.sleep(1000)
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("Barcelona"), pressImeActionButton())
        Thread.sleep(5000)
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
        Thread.sleep(1000)
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("Not Found Item"), pressImeActionButton())
        Thread.sleep(5000)
        onView(withId(R.id.no_result)).check(matches(isDisplayed()))
    }
}