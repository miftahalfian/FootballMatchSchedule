package com.solitelab.footballmatchschedule.ui.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.gson.Gson
import com.solitelab.footballmatchschedule.EspressoIdlingResource

import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.data.adapter.TeamAdapter
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.mvp.model.League
import com.solitelab.footballmatchschedule.data.mvp.model.Team
import com.solitelab.footballmatchschedule.data.mvp.team.TeamPresenter
import com.solitelab.footballmatchschedule.data.mvp.team.TeamView
import com.solitelab.footballmatchschedule.ui.TeamDetailActivity
import com.solitelab.footballmatchschedule.ui.layout.MatchList
import com.solitelab.footballmatchschedule.ui.layout.NoResult
import com.solitelab.footballmatchschedule.utils.gone
import com.solitelab.footballmatchschedule.utils.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.defaultSharedPreferences
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class TeamFragment : Fragment(), TeamView {
    lateinit var presenter: TeamPresenter
    lateinit var league: League

    lateinit var swipe: SwipeRefreshLayout
    lateinit var rvTeam: RecyclerView
    lateinit var noResult: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val matchListUI = MatchList()
        val noResultUI = NoResult()

        val layout = UI {
            relativeLayout {
                lparams(matchParent, matchParent)

                swipe = ankoView({ matchListUI.createView(AnkoContext.create(it)) }, 0) {
                }

                noResult = ankoView({ noResultUI.createView(AnkoContext.create(it)) }, 0) {
                }.lparams(wrapContent, wrapContent) {
                    centerHorizontally()
                    centerVertically()
                }

            }
        }.view

        rvTeam = matchListUI.recyclerView

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val json = defaultSharedPreferences.getString("current_league", "")
        league = Gson().fromJson(json, League::class.java)


        presenter = TeamPresenter(this)
        EspressoIdlingResource.setIdleState(false)
        presenter.getTeams(league.id)

        swipe.onRefresh {
            EspressoIdlingResource.setIdleState(false)
            presenter.getTeams(league.id)
        }

        rvTeam.layoutManager = LinearLayoutManager(context)
        rvTeam.adapter = TeamAdapter() {
            startActivity<TeamDetailActivity>("team" to it)
        }

        noResult.gone()
    }

    override fun onLoadData() {
        swipe.isRefreshing = true
    }

    override fun onDataLoaded(data: List<Team>) {
        EspressoIdlingResource.setIdleState(true)
        swipe.isRefreshing = false
        (rvTeam.adapter as TeamAdapter).setData(data)
        noResult.gone()
    }

    override fun onLoadFailed() {
        swipe.isRefreshing = false
        noResult.visible()
    }
}
