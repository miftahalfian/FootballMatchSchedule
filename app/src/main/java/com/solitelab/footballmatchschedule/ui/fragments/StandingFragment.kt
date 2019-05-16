package com.solitelab.footballmatchschedule.ui.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.solitelab.footballmatchschedule.EspressoIdlingResource

import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.data.adapter.StandingAdapter
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.mvp.model.League
import com.solitelab.footballmatchschedule.data.mvp.model.Standing
import com.solitelab.footballmatchschedule.data.mvp.standing.StandingPresenter
import com.solitelab.footballmatchschedule.data.mvp.standing.StandingView
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.defaultSharedPreferences
import org.jetbrains.anko.support.v4.onRefresh

class StandingFragment : Fragment(), StandingView {
    lateinit var presenter: StandingPresenter
    lateinit var league: League
    lateinit var swipe: SwipeRefreshLayout
    lateinit var rvStanding: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_standing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val json = defaultSharedPreferences.getString("current_league", "")
        league = Gson().fromJson(json, League::class.java)

        swipe = view.find(R.id.swipe)
        rvStanding = view.find(R.id.rvStanding)

        presenter = StandingPresenter(this, Gson(), ApiRepository())
        EspressoIdlingResource.setIdleState(false)
        presenter.getStandings(league.id)

        swipe.onRefresh {
            EspressoIdlingResource.setIdleState(false)
            presenter.getStandings(league.id)
        }

        rvStanding.layoutManager = LinearLayoutManager(context)
        rvStanding.adapter = StandingAdapter()

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onLoadData() {
        swipe.isRefreshing = true
    }

    override fun onDataLoaded(data: List<Standing>) {
        EspressoIdlingResource.setIdleState(true)
        swipe.isRefreshing = false
        (rvStanding.adapter as StandingAdapter).setData(data)
    }
}
