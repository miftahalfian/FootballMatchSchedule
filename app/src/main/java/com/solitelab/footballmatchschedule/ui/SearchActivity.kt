package com.solitelab.footballmatchschedule.ui

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.solitelab.footballmatchschedule.EspressoIdlingResource
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.data.adapter.PageAdapter
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.mvp.search.SearchPresenter
import com.solitelab.footballmatchschedule.ui.fragments.*
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.appcompat.v7.coroutines.onQueryTextListener


class SearchActivity : AppCompatActivity() {
    private lateinit var searchItem: MenuItem
    private lateinit var searchView: SearchView
    private lateinit var presenter: SearchPresenter

    private lateinit var matchSearch : SearchMatchFragment
    private lateinit var teamSearch : SearchTeamFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        matchSearch = SearchMatchFragment()
        teamSearch = SearchTeamFragment()

        presenter = SearchPresenter(matchSearch, teamSearch)

        matchSearch.listener = {
            presenter.refreshSearch()
        }

        matchSearch.listener = {
            presenter.refreshSearch()
        }

        initDeclaration()

    }

    private fun initDeclaration() {

        val tabsTitle = ArrayList<String>()
        tabsTitle.add("MATCH")
        tabsTitle.add("TEAM")

        val tabsFragment = ArrayList<Fragment>()
        tabsFragment.add(matchSearch)
        tabsFragment.add(teamSearch)

        val pageAdapter = PageAdapter(supportFragmentManager, tabsFragment, tabsTitle)
        container.adapter = pageAdapter
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))

        tabs.setupWithViewPager(container)

        tabs.getTabAt(0)?.setIcon(R.drawable.ic_football_field)
        tabs.getTabAt(1)?.setIcon(R.drawable.ic_team)
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
                EspressoIdlingResource.setIdleState(false)
                presenter.search(it)
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

}
