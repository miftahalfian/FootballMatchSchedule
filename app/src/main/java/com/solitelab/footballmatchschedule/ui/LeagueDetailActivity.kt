package com.solitelab.footballmatchschedule.ui

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.ui.layout.LeagueDetailUI
import com.solitelab.footballmatchschedule.data.mvp.model.LeagueDetail
import com.squareup.picasso.Picasso
import org.jetbrains.anko.setContentView

class LeagueDetailActivity : AppCompatActivity() {
    private val ui = LeagueDetailUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui.setContentView(this)

        val league = intent.getParcelableExtra<LeagueDetail>("league")

        setSupportActionBar(ui.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = league.name

        ui.collapsingToolbar.title = league.name

        ui.leagueName.text = league.name
        ui.leagueId.text = "%s %s".format(resources.getString(R.string.league_id), league.id)
        ui.leagueDesc.text = league.description
        ui.leagueCountry.text = league.country
        ui.leagueFormed.text = league.formed
        ui.leagueWebsite.text = league.website

        league.badge?.let { Picasso.get().load(it).into(ui.leagueLogo) }
        league.fanArt?.let { Picasso.get().load(it).into(ui.leagueBanner) }

        ui.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, _ ->
            val offsetAlpha = (appBarLayout.y / appBarLayout.totalScrollRange)
            ui.toolbar.setTitleTextColor(Color.argb(((offsetAlpha * -1) * 255).toInt(), 255, 255, 255))
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                finishAfterTransition()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
