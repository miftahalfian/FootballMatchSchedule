package com.solitelab.footballmatchschedule.ui

import android.app.ActivityOptions
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.widget.GridLayoutManager
import com.solitelab.footballmatchschedule.data.adapter.LeagueAdapter
import com.solitelab.footballmatchschedule.ui.layout.MainUI
import com.solitelab.footballmatchschedule.data.mvp.model.League
import com.solitelab.footballmatchschedule.data.mvp.main.MainPresenter
import com.solitelab.footballmatchschedule.data.mvp.main.MainView
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.setContentView

class MainActivity : AppCompatActivity(), MainView {
    private val ui : MainUI = MainUI()
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui.setContentView(this)

        presenter = MainPresenter(this)
        presenter.setupData()

        ui.leagueList.layoutManager = GridLayoutManager(this, 2)
    }

    override fun setData(items: MutableList<League>) {
        ui.leagueList.adapter = LeagueAdapter(items) {
            val options = ActivityOptions
                .makeSceneTransitionAnimation(this, it.second, ViewCompat.getTransitionName(it.second))
            startActivity(intentFor<MatchActivity>("league" to it.first), options.toBundle())
        }
    }
}
