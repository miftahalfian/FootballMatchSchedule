package com.solitelab.footballmatchschedule.ui

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.data.adapter.PageAdapter
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.mvp.leaguedetail.LeagueDetailPresenter
import com.solitelab.footballmatchschedule.data.mvp.leaguedetail.LeagueDetailView
import com.solitelab.footballmatchschedule.data.mvp.model.League
import com.solitelab.footballmatchschedule.data.mvp.model.LeagueDetail
import com.solitelab.footballmatchschedule.ui.fragments.*
import com.solitelab.footballmatchschedule.ui.layout.MatchUI
import com.squareup.picasso.Picasso
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity

class MatchActivity : AppCompatActivity(), LeagueDetailView {
    private lateinit var presenter : LeagueDetailPresenter
    private val appActivity = this
    private var leagueDetail: LeagueDetail? = null

    val ui = MatchUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_match)
        ui.setContentView(this)

        initDeclaration()

        val league : League

        if (intent.getParcelableExtra("league") as League? != null) {
            league = intent.getParcelableExtra("league")

            val json = Gson().toJson(league)
            defaultSharedPreferences.edit().putString("current_league", json).apply()
        }
        else {
            val json = defaultSharedPreferences.getString("current_league", "")
            league = Gson().fromJson(json, League::class.java)
        }

        setSupportActionBar(ui.toolbar)
        supportActionBar?.title = league?.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val request = ApiRepository()
        val gson = Gson()
        presenter = LeagueDetailPresenter(this, gson, request)
        presenter.getLeagueDetail(league?.id.toString())

        league?.image?.let {
            Picasso.get().load(it).into(ui.leagueLogo)
        }

        ui.leagueName.text = league?.name
        ui.leagueAlternative.text = "%s %s".format(getString(R.string.alternative), "-")
        ui.leagueCountry.text = "%s %s".format(getString(R.string.country), "-")
        ui.leagueFormed.text = "%s %s".format(getString(R.string.formed), "-")
        ui.leagueWebsite.text = "%s %s".format(getString(R.string.website), "-")

        ui.leagueWebsite.onClick {
            val text = ui.leagueWebsite.text
            val arr = text.split(":")
            var website = arr[1].trim()

            if (!website.startsWith("http://") && !website.startsWith("https://")) {
                website = "http://$website"
            }

            val uris = Uri.parse(website)
            val intent = Intent(Intent.ACTION_VIEW, uris)
            startActivity(intent)
        }

        ui.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, _ ->
            val offsetAlpha = (appBarLayout.y / appBarLayout.totalScrollRange)
            ui.toolbar.setTitleTextColor(Color.argb(((offsetAlpha * -1) * 255).toInt(), 255, 255, 255))
        })
    }

    private fun initDeclaration() {

        val tabsTitle = ArrayList<String>()
        tabsTitle.add("MATCHES")
        tabsTitle.add("STANDINGS")
        tabsTitle.add("TEAMS")

        val tabsFragment = ArrayList<Fragment>()
        tabsFragment.add(MatchFragment())
        tabsFragment.add(StandingFragment())
        tabsFragment.add(TeamFragment())

        val pageAdapter = PageAdapter(supportFragmentManager, tabsFragment, tabsTitle)
        ui.viewPager.adapter = pageAdapter
        ui.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(ui.tabLayout))

        ui.tabLayout.setupWithViewPager(ui.viewPager)
    }

    override fun showLeagueDetail(detail: LeagueDetail?) {
        leagueDetail = detail
        ui.leagueAlternative.text = "%s %s".format(
            getString(R.string.alternative),
            if (detail?.alternate!!.isNotEmpty()) detail.alternate else "-")

        ui.leagueCountry.text = "%s %s".format(getString(R.string.country), detail.country)
        ui.leagueFormed.text = "%s %s".format(getString(R.string.formed), detail.formed)

        val udata = "%s %s".format(getString(R.string.website), detail.website)
        val content = SpannableString(udata)
        content.setSpan(UnderlineSpan(), 0, udata.length, 0)

        ui.leagueWebsite.text = content
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_league_detail, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                finishAfterTransition()
                true
            }
            R.id.detail -> {
                if (leagueDetail != null) {
                    val options = ActivityOptions.makeSceneTransitionAnimation(
                        appActivity,
                        ui.leagueLogo,
                        ViewCompat.getTransitionName(ui.leagueLogo)
                    )
                    startActivity(intentFor<LeagueDetailActivity>("league" to leagueDetail), options.toBundle())
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
