package com.solitelab.footballmatchschedule.ui.fragments

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
import android.widget.TextView
import com.google.gson.Gson
import com.solitelab.footballmatchschedule.EspressoIdlingResource
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.data.adapter.MatchAdapter
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.mvp.match.MatchPresenter
import com.solitelab.footballmatchschedule.data.mvp.match.MatchView
import com.solitelab.footballmatchschedule.data.mvp.model.League
import com.solitelab.footballmatchschedule.data.mvp.model.Match
import com.solitelab.footballmatchschedule.ui.MatchDetailActivity
import com.solitelab.footballmatchschedule.utils.gone
import com.solitelab.footballmatchschedule.utils.visible
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.defaultSharedPreferences
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh

class MatchFragment : Fragment(), MatchView {
    lateinit var presenter: MatchPresenter
    lateinit var league: League
    lateinit var rvLastMatch: RecyclerView
    lateinit var rvNextMatch: RecyclerView
    lateinit var tvLastMatch: TextView
    lateinit var tvNextMatch: TextView
    lateinit var swipe: SwipeRefreshLayout

    var isLastMatchFinished = false
    var isNextMatchFinished = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_match, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val json = defaultSharedPreferences.getString("current_league", "")
        league = Gson().fromJson(json, League::class.java)

        rvLastMatch = view.find(R.id.rv_last_match)
        rvNextMatch = view.find(R.id.rv_next_match)
        tvLastMatch = view.find(R.id.tv_last_match)
        tvNextMatch = view.find(R.id.tv_next_match)
        swipe = view.find(R.id.swipe)

        presenter = MatchPresenter(this, Gson(), ApiRepository())
        EspressoIdlingResource.setIdleState(false)
        presenter.getLastMatch(league.id)
        presenter.getNextMatch(league.id)

        isLastMatchFinished = false
        isNextMatchFinished = false

        rvLastMatch.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
        rvLastMatch.adapter = MatchAdapter {
                match, homeLogo, awayLogo, homeSrc, awaySrc -> goToDetail(match, homeLogo, awayLogo, homeSrc, awaySrc)
        }

        rvNextMatch.layoutManager = LinearLayoutManager(context, LinearLayout.HORIZONTAL, false)
        rvNextMatch.adapter = MatchAdapter {
                match, homeLogo, awayLogo, homeSrc, awaySrc -> goToDetail(match, homeLogo, awayLogo, homeSrc, awaySrc)
        }

        swipe.onRefresh {
            EspressoIdlingResource.setIdleState(false)
            presenter.getLastMatch(league.id)
            presenter.getNextMatch(league.id)

            isLastMatchFinished = false
            isNextMatchFinished = false
        }
    }

    private fun goToDetail(match: Match, homeLogo: ImageView, awayLogo: ImageView, homeSrc: String, awaySrc: String) {
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
        swipe.isRefreshing = true
    }

    override fun onLoadFailed(type: String) {
        when(type) {
            "LastMatch" -> {
                tvLastMatch.gone()
                rvLastMatch.gone()
                isLastMatchFinished = true
            }
            "NextMatch" -> {
                tvNextMatch.gone()
                rvNextMatch.gone()
                isNextMatchFinished = true
            }
        }
        swipe.isRefreshing = false
        EspressoIdlingResource.setIdleState(isLastMatchFinished && isNextMatchFinished)
    }

    override fun onDataLoaded(data: List<Match>, type: String) {
        when(type) {
            "LastMatch" -> {
                tvLastMatch.visible()
                rvLastMatch.visible()
                (rvLastMatch.adapter as MatchAdapter).setData(data)
                isLastMatchFinished = true
            }
            "NextMatch" -> {
                tvNextMatch.visible()
                rvNextMatch.visible()
                (rvNextMatch.adapter as MatchAdapter).setData(data)
                isNextMatchFinished = true
            }
        }
        swipe.isRefreshing = false
        EspressoIdlingResource.setIdleState(isLastMatchFinished && isNextMatchFinished)
    }
}
