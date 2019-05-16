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
import com.solitelab.footballmatchschedule.data.mvp.model.Team
import com.solitelab.footballmatchschedule.data.mvp.search.TeamSearch
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
import org.jetbrains.anko.support.v4.toast

class SearchTeamFragment : Fragment(), TeamSearch {

    private lateinit var swipeContainer : SwipeRefreshLayout
    private lateinit var matchList : RecyclerView
    private lateinit var noResultLayout : LinearLayout

    var listener: () -> Unit = {}

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


        swipeContainer.onRefresh {
            listener()
        }

        noResultLayout.gone()
    }


    override fun onLoadData() {
        swipeContainer.isRefreshing = true
    }

    override fun onDataLoaded(team: List<Team>) {
        EspressoIdlingResource.setIdleState(true)
        swipeContainer.isRefreshing = false

        val data = ArrayList<Team>()
        for(t in team) {
            if (t.strSport == "Soccer") {
                data.add(t)
            }
        }

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

    override fun onLoadFailed() {
        EspressoIdlingResource.setIdleState(true)
        matchList.gone()
        noResultLayout.visible()
        swipeContainer.isRefreshing = false
    }

}
