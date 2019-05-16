package com.solitelab.footballmatchschedule.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.solitelab.footballmatchschedule.EspressoIdlingResource
import com.solitelab.footballmatchschedule.data.adapter.TeamAdapter
import com.solitelab.footballmatchschedule.data.mvp.favorite.FavoriteTeamPresenter
import com.solitelab.footballmatchschedule.data.mvp.favorite.FavoriteTeamView
import com.solitelab.footballmatchschedule.data.mvp.model.Team
import com.solitelab.footballmatchschedule.ui.TeamDetailActivity
import com.solitelab.footballmatchschedule.ui.layout.MatchList
import com.solitelab.footballmatchschedule.ui.layout.NoResult
import com.solitelab.footballmatchschedule.utils.gone
import com.solitelab.footballmatchschedule.utils.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.startActivity

class FavoriteTeamFragment : Fragment(), FavoriteTeamView {
    private lateinit var swipeContainer : SwipeRefreshLayout
    private lateinit var matchList : RecyclerView
    private lateinit var noResultLayout : LinearLayout
    lateinit var presenter: FavoriteTeamPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val matchListUI = MatchList()
        val noResultUI = NoResult()

        val layout = UI {
            relativeLayout {
                lparams(matchParent, matchParent)

                swipeContainer = ankoView({ matchListUI.createView(AnkoContext.create(it)) }, 0) {
                }

                noResultLayout = ankoView({ noResultUI.createView(AnkoContext.create(it)) }, 0) {
                }.lparams(wrapContent, wrapContent) {
                    centerHorizontally()
                    centerVertically()
                }

            }
        }.view

        matchList = matchListUI.recyclerView

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        matchList.adapter = TeamAdapter {
            startActivity<TeamDetailActivity>("team" to it)
        }

        presenter = FavoriteTeamPresenter(this)
        EspressoIdlingResource.setIdleState(false)
        presenter.loadTeamFavorite(context)

        swipeContainer.onRefresh {
            EspressoIdlingResource.setIdleState(false)
            presenter.loadTeamFavorite(context)
        }
    }

    override fun onLoadData() {
        swipeContainer.isRefreshing = true
    }

    override fun onDataLoaded(data: List<Team>) {
        EspressoIdlingResource.setIdleState(true)
        swipeContainer.isRefreshing = false

        (matchList.adapter as TeamAdapter).setData(data)

        if (data.isNotEmpty()) {
            matchList.visible()
            noResultLayout.gone()
        }
        else {
            matchList.gone()
            noResultLayout.visible()
        }
    }

    override fun onResume() {
        EspressoIdlingResource.setIdleState(true)
        presenter.loadTeamFavorite(context)
        super.onResume()
    }
}
