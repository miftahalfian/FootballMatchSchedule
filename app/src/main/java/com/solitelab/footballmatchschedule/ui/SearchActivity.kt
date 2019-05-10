package com.solitelab.footballmatchschedule.ui

import android.app.ActivityOptions
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import com.google.gson.Gson
import com.solitelab.footballmatchschedule.EspressoIdlingResource
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.data.adapter.MatchAdapter
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.mvp.model.Match
import com.solitelab.footballmatchschedule.data.mvp.search.SearchPresenter
import com.solitelab.footballmatchschedule.ui.layout.MatchListUI
import com.solitelab.footballmatchschedule.utils.invisible
import com.solitelab.footballmatchschedule.utils.visible
import org.jetbrains.anko.appcompat.v7.coroutines.onQueryTextListener
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.support.v4.onRefresh


class SearchActivity : AppCompatActivity(), com.solitelab.footballmatchschedule.data.mvp.search.SearchView {
    private lateinit var searchItem: MenuItem
    private lateinit var searchView: SearchView
    private lateinit var presenter: SearchPresenter
    private var queryString: String? = ""

    private val ui = MatchListUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui.setContentView(this)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Search Football Match"

        val request = ApiRepository()
        val gson = Gson()
        presenter = SearchPresenter(this, gson, request)
        ui.noResult.invisible()

        ui.matchList.adapter = MatchAdapter {
            match, homeLogo, awayLogo, homeSrc, awaySrc -> goToResult(match, homeLogo, awayLogo, homeSrc, awaySrc)
        }

        ui.swipeContainer.onRefresh {
            EspressoIdlingResource.setIdleState(false)
            presenter.search(queryString)
        }

    }

    private fun goToResult(match:Match, homeLogo:ImageView, awayLogo:ImageView, homeSrc:String, awaySrc:String) {
        val pairs = ArrayList<android.util.Pair<View, String>>()
        val pairHome = android.util.Pair.create<View, String>(homeLogo, ViewCompat.getTransitionName(homeLogo)!!)
        val pairAway = android.util.Pair.create<View, String>(awayLogo, ViewCompat.getTransitionName(awayLogo)!!)
        pairs.add(pairHome)
        pairs.add(pairAway)

        val pairArray = pairs.toTypedArray()

        val options = ActivityOptions
            .makeSceneTransitionAnimation(this, *pairArray)
        startActivity(intentFor<MatchDetailActivity>(
            "match" to match,
            "homeSrc" to homeSrc,
            "awaySrc" to awaySrc
        ), options.toBundle())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        searchItem = menu?.findItem(R.id.search)!!
        searchItem.expandActionView()
        searchView = searchItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search)

        searchView.onQueryTextListener {
            onQueryTextSubmit {
                queryString = it
                presenter.search(it)
                queryString = it
                EspressoIdlingResource.setIdleState(false)
                true
            }
        }

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                // TODO: do something...
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                finish()
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onLoadData() {
        ui.swipeContainer.isRefreshing = true
    }

    override fun onDataLoaded(matches: List<Match>?) {
        ui.swipeContainer.isRefreshing = false
        (ui.matchList.adapter as MatchAdapter).clear()
        if (matches != null) {
            ui.matchList.visible()
            (ui.matchList.adapter as MatchAdapter).setData(matches)
            ui.noResult.invisible()
        }
        else {
            ui.matchList.invisible()
            ui.noResult.visible()
        }
        EspressoIdlingResource.setIdleState(true)
    }

}
