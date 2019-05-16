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
import com.google.gson.Gson
import com.solitelab.footballmatchschedule.EspressoIdlingResource
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.data.adapter.PlayerAdapter
import com.solitelab.footballmatchschedule.data.adapter.StandingAdapter
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.mvp.model.Player
import com.solitelab.footballmatchschedule.data.mvp.model.Team
import com.solitelab.footballmatchschedule.data.mvp.player.PlayerPresenter
import com.solitelab.footballmatchschedule.data.mvp.player.PlayerView
import com.solitelab.footballmatchschedule.ui.MatchActivity
import com.solitelab.footballmatchschedule.ui.PlayerDetailActivity
import com.solitelab.footballmatchschedule.ui.layout.MatchList
import com.solitelab.footballmatchschedule.ui.layout.NoResult
import com.solitelab.footballmatchschedule.utils.gone
import com.solitelab.footballmatchschedule.utils.invisible
import com.solitelab.footballmatchschedule.utils.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh

class PlayerFragment : Fragment(), PlayerView {
    var team : Team? = null
    lateinit var presenter: PlayerPresenter
    lateinit var swipe: SwipeRefreshLayout
    lateinit var rvPlayer: RecyclerView
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

        rvPlayer = matchListUI.recyclerView

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        team = activity?.intent?.getParcelableExtra("team")

        presenter = PlayerPresenter(this, Gson(), ApiRepository())
        EspressoIdlingResource.setIdleState(false)
        presenter.getPlayers(team?.idTeam)

        rvPlayer.layoutManager = LinearLayoutManager(context)
        rvPlayer.adapter = PlayerAdapter {
            icon, player ->  goToPlayerDetails(icon, player)
        }

        swipe.onRefresh {
            EspressoIdlingResource.setIdleState(false)
            presenter.getPlayers(team?.idTeam)
        }

        noResult.gone()
    }

    private fun goToPlayerDetails(icon: ImageView, player: Player) {
        val options = ActivityOptions
            .makeSceneTransitionAnimation(activity, icon, ViewCompat.getTransitionName(icon))
        startActivity(intentFor<PlayerDetailActivity>("player" to player), options.toBundle())
    }

    override fun onLoadData() {
        swipe.isRefreshing = true
    }

    override fun onDataLoaded(player: List<Player>) {
        EspressoIdlingResource.setIdleState(true)
        swipe.isRefreshing = false
        (rvPlayer.adapter as PlayerAdapter).setData(player)
        rvPlayer.visible()
        noResult.gone()
    }

    override fun onLoadFailed() {
        EspressoIdlingResource.setIdleState(true)
        swipe.isRefreshing = false
        rvPlayer.invisible()
        noResult.visible()
    }
}
