package com.solitelab.footballmatchschedule.ui

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.solitelab.footballmatchschedule.EspressoIdlingResource
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.R.id.*
import com.solitelab.footballmatchschedule.Util.withIndex
import com.solitelab.footballmatchschedule.data.mvp.model.League
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MatchActivityTest {
    @Rule
    @JvmField var mActivityRule: ActivityTestRule<MatchActivity> =
        object : ActivityTestRule<MatchActivity>(MatchActivity::class.java) {
            override fun getActivityIntent(): Intent {
                val targetContext = InstrumentationRegistry.getInstrumentation()
                    .targetContext

                //sample data
                val league = League("English Premier League", R.drawable.english_premier_league, 4328)

                //atur jadi intent extra
                val result = Intent(targetContext, MatchActivity::class.java)
                result.putExtra("league", league)
                return result
            }
        }

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun leagueDetailTest() {
        //cek apakah logo ditampilkan
        onView(withId(league_logo))
            .check(matches(isDisplayed()))

        //cek apakah nama liga ditampilkan & isi teksnya sesuai dengan yg diharapkan
        onView(withId(league_name_top))
            .check(matches(withText("English Premier League")))

        //buka menu action bar
        openActionBarOverflowOrOptionsMenu(mActivityRule.activity)

        //jika ada menu yg ada tulisannya Detail, diklik
        onView(withText("Detail")).perform(click())

        //cek apakah di nama liga ada tulisan English Premier League
        onView(withId(league_name)).check(matches(withText("English Premier League")))
    }

    @Test
    fun lastMatchRecyclerViewTest() {
        //cek apakah view dengan id match_list yg ke index-0 tampil di layar
        onView(withIndex(withId(match_list), 0))
            .check(matches(isDisplayed()))

        //lakukan aksi scroll ke posisi item ke-5
        onView(withIndex(withId(match_list), 0))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))

        //lakukan aksi klik pada item ke-5
        onView(withIndex(withId(match_list), 0))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))
    }

    @Test
    fun nextMatchRecyclerViewTest() {
        //klik tab NEXT MATCH
        onView(withText("NEXT MATCH")).perform(click())

        //cek apakah view dengan id match_list yg ke index-0 tampil di layar
        onView(withIndex(withId(match_list), 1))
            .check(matches(isDisplayed()))

        //lakukan aksi scroll ke posisi item ke-5
        onView(withIndex(withId(match_list), 1))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))

        //lakukan aksi klik pada item ke-5
        onView(withIndex(withId(match_list), 1))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }
}