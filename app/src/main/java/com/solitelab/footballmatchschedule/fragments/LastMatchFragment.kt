package com.solitelab.footballmatchschedule.fragments

import android.app.ActivityOptions
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.google.gson.Gson
import com.solitelab.footballmatchschedule.MatchDetailActivity
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.adapter.MatchAdapter
import com.solitelab.footballmatchschedule.api.ApiRepository
import com.solitelab.footballmatchschedule.mvp.match.lastmatch.LastMatchPresenter
import com.solitelab.footballmatchschedule.mvp.match.lastmatch.LastMatchView
import com.solitelab.footballmatchschedule.mvp.model.League
import com.solitelab.footballmatchschedule.mvp.model.Match
import com.solitelab.footballmatchschedule.utils.invisible
import com.solitelab.footballmatchschedule.utils.visible
import org.jetbrains.anko.support.v4.*

class LastMatchFragment : Fragment(), LastMatchView {
    lateinit var presenter : LastMatchPresenter
    lateinit var swipeContainer : SwipeRefreshLayout
    lateinit var matchList : RecyclerView
    lateinit var noResultLayout : LinearLayout
    lateinit var league: League

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_last_match, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val json = defaultSharedPreferences.getString("current_league", "")
        league = Gson().fromJson(json, League::class.java)

        swipeContainer = find(R.id.swipeContainer)
        matchList = find(R.id.matchList)
        noResultLayout = find(R.id.no_result_layout)

        matchList.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        matchList.adapter = MatchAdapter() {
                match, homeLogo, awayLogo, homeSrc, awaySrc -> goToDetail(match, homeLogo, awayLogo, homeSrc, awaySrc)
        }

        swipeContainer.onRefresh {
            presenter.getLastMatch(league.id)
        }

        val request = ApiRepository()
        val gson = Gson()
        presenter = LastMatchPresenter(this, gson, request)
        presenter.getLastMatch(league.id)

        noResultLayout.invisible()
    }

    fun goToDetail(match:Match, homeLogo:ImageView, awayLogo:ImageView, homeSrc:String, awaySrc:String) {
        val pairs = ArrayList<android.util.Pair<View, String>>()
        val pairHome = android.util.Pair.create<View, String>(homeLogo, ViewCompat.getTransitionName(homeLogo)!!)
        val pairAway = android.util.Pair.create<View, String>(awayLogo, ViewCompat.getTransitionName(awayLogo)!!)
        pairs.add(pairHome)
        pairs.add(pairAway)

        val pairArray = pairs.toTypedArray()

        val options = ActivityOptions
            .makeSceneTransitionAnimation(activity, *pairArray)
        startActivity(intentFor<MatchDetailActivity>(
            "match" to match,
            "homeSrc" to homeSrc,
            "awaySrc" to awaySrc
        ), options.toBundle())
    }

    override fun onLoadData() {
        swipeContainer.isRefreshing = true
    }

    override fun onDataLoaded(matches: List<Match>) {
        swipeContainer.isRefreshing = false
        (matchList.adapter as MatchAdapter).setData(matches)
        matchList.visible()
        noResultLayout.invisible()
    }

    override fun onLoadFailed() {
        swipeContainer.isRefreshing = false
        matchList.invisible()
        noResultLayout.visible()
    }
}
