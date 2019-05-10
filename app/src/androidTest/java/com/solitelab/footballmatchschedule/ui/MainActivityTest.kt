package com.solitelab.footballmatchschedule.ui

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.solitelab.footballmatchschedule.EspressoIdlingResource
import com.solitelab.footballmatchschedule.R.id.*
import com.solitelab.footballmatchschedule.Util
import com.solitelab.footballmatchschedule.Util.withIndex
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun testRecyclerViewBehaviour() {
        //cek apakah league_list ditampilkan di layar
        onView(withId(league_list))
            .check(matches(isDisplayed()))

        //lakukan aksi scroll ke posisi item ke-7
        onView(withId(league_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(7))

        //lakukan aksi click pada item ke-7
        onView(withId(league_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(7, click()))
    }

    @Test
    fun lastMatchFavoriteTest() {
        //klik view pada posisi 0
        onView(withId(league_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        //cek apakah view dengan id match_list yg ke index-0 tampil di layar
        onView(withIndex(withId(match_list), 0))
            .check(matches(isDisplayed()))

        //klik view yg teksnya sama dengan Leicester
        onView(withIndex(withText("Leicester"), 0)).perform(click())

        //cek apakah tombol favorit ditampilkan di layar
        onView(withId(favorite))
            .check(matches(isDisplayed()))

        //lakukan aksi klik pada tombol favorit
        onView(withId(favorite)).perform(click())

        //pencet back (Kembali ke MatchActivity)
        pressBack()

        //pencet back (Kembali ke MainActivity)
        pressBack()

        //buka menu action bar
        openActionBarOverflowOrOptionsMenu(activityRule.activity)

        //pencet menu Favorite Match
        onView(withText("Favorite Match")).perform(click())

        //cek view yang memiliki teks Leicester ditampilkan
        onView(withIndex(withId(match_list), 0)).check(
            matches(
                Util.hasItemm(
                    hasDescendant(
                        withText(
                            Matchers.containsString("Leicester")
                        )
                    )
                )
            )
        )
    }

    @Test
    fun nextMatchFavoriteTest() {
        //klik view pada posisi 0
        onView(withId(league_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        //klik tab NEXT MATCH
        onView(withText("NEXT MATCH")).perform(click())

        //cek apakah view dengan id match_list yg ke index-1 tampil di layar
        onView(withIndex(withId(match_list), 1))
            .check(matches(isDisplayed()))

        //lakukan aksi klik pada view dengan tulisan Liverpool
        onView(withIndex(withText("Liverpool"), 0)).perform(click())

        //cek apakah tombol favorite tampil di layar
        onView(withId(favorite))
            .check(matches(isDisplayed()))

        //lakukan aksi klik pada tombol favorite
        onView(withId(favorite)).perform(click())

        //pencet back (Kembali ke MatchActivity)
        pressBack()

        //pencet back (Kembali ke MainActivity)
        pressBack()

        //buka menu action bar
        openActionBarOverflowOrOptionsMenu(activityRule.activity)

        //pencet menu Favorite Match
        onView(withText("Favorite Match")).perform(click())

        //lakukan aksi klik pada tab NEXT MATCH
        onView(withText("NEXT MATCH")).perform(click())

        //cek apakah ada view dengan tulisan Liverpool
        //cek view yang memiliki teks tersebut ditampilkan
        onView(withIndex(withId(match_list), 1)).check(
            matches(
                Util.hasItemm(
                    hasDescendant(
                        withText(
                            Matchers.containsString("Liverpool")
                        )
                    )
                )
            )
        )
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }
}