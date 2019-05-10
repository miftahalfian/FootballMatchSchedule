package com.solitelab.footballmatchschedule.ui

import android.app.ActivityOptions
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.data.adapter.LeagueAdapter
import com.solitelab.footballmatchschedule.ui.layout.MainUI
import com.solitelab.footballmatchschedule.data.mvp.model.League
import com.solitelab.footballmatchschedule.data.mvp.main.MainPresenter
import com.solitelab.footballmatchschedule.data.mvp.main.MainView
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), MainView {
    private val ui: MainUI = MainUI()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.favorite -> {
                startActivity<FavoriteActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}