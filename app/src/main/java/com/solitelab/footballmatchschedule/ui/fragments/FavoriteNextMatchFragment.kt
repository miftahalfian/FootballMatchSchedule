package com.solitelab.footballmatchschedule.ui.fragments

import android.app.ActivityOptions
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.solitelab.footballmatchschedule.data.adapter.MatchAdapter
import com.solitelab.footballmatchschedule.data.mvp.favorite.FavoriteMatchPresenter
import com.solitelab.footballmatchschedule.data.mvp.favorite.FavoriteMatchView
import com.solitelab.footballmatchschedule.data.mvp.model.Match
import com.solitelab.footballmatchschedule.ui.MatchDetailActivity
import com.solitelab.footballmatchschedule.ui.layout.MatchList
import com.solitelab.footballmatchschedule.ui.layout.NoResult
import com.solitelab.footballmatchschedule.utils.gone
import com.solitelab.footballmatchschedule.utils.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh

class FavoriteNextMatchFragment : Fragment(), FavoriteMatchView {
    private lateinit var swipeContainer : SwipeRefreshLayout
    private lateinit var matchList : RecyclerView
    private lateinit var noResultLayout : LinearLayout
    private lateinit var presenter: FavoriteMatchPresenter

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

        matchList.adapter = MatchAdapter {
                match, homeLogo, awayLogo, homeSrc, awaySrc -> goToDetail(match, homeLogo, awayLogo, homeSrc, awaySrc)
        }

        presenter = FavoriteMatchPresenter(this)
        presenter.loadNextMatchFavorite(context)

        swipeContainer.onRefresh {
            presenter.loadNextMatchFavorite(context)
        }

        noResultLayout.gone()
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
        swipeContainer.isRefreshing = true
    }

    override fun onDataLoaded(matches: List<Match>) {
        swipeContainer.isRefreshing = false
        (matchList.adapter as MatchAdapter).setData(matches)

        if (matches.isNotEmpty()) {
            matchList.visible()
            noResultLayout.gone()
        }
        else {
            matchList.gone()
            noResultLayout.visible()
        }
    }

    override fun onResume() {
        super.onResume()

        presenter.loadNextMatchFavorite(context)
    }
}
