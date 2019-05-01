package com.solitelab.footballmatchschedule.ui

import android.app.ActivityOptions
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.gson.Gson
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.data.adapter.MatchAdapter
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.mvp.model.Match
import com.solitelab.footballmatchschedule.data.mvp.search.SearchPresenter
import com.solitelab.footballmatchschedule.utils.invisible
import com.solitelab.footballmatchschedule.utils.visible
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.match_list_layout.*
import org.jetbrains.anko.appcompat.v7.coroutines.onQueryTextListener
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.support.v4.onRefresh



class SearchActivity : AppCompatActivity(), com.solitelab.footballmatchschedule.data.mvp.search.SearchView {
    private lateinit var searchItem: MenuItem
    private lateinit var searchView: SearchView
    private lateinit var presenter: SearchPresenter
    private var queryString: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Search Football Match"

        val request = ApiRepository()
        val gson = Gson()
        presenter = SearchPresenter(this, gson, request)
        no_result_layout.invisible()

        matchList.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        matchList.adapter = MatchAdapter {
            match, homeLogo, awayLogo, homeSrc, awaySrc -> goToResult(match, homeLogo, awayLogo, homeSrc, awaySrc)
        }

        swipeContainer.onRefresh {
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
                presenter.search(it)
                queryString = it
                true
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onLoadData() {
        swipeContainer.isRefreshing = true
    }

    override fun onDataLoaded(matches: List<Match>?) {
        swipeContainer.isRefreshing = false
        (matchList.adapter as MatchAdapter).clear()
        if (matches != null) {
            matchList.visible()
            (matchList.adapter as MatchAdapter).setData(matches)
            no_result_layout.invisible()
        }
        else {
            matchList.invisible()
            no_result_layout.visible()
        }
    }

}
