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
import com.solitelab.footballmatchschedule.data.adapter.MatchPageAdapter
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.ui.fragments.LastMatchFragment
import com.solitelab.footballmatchschedule.ui.fragments.NextMatchFragment
import com.solitelab.footballmatchschedule.data.mvp.match.MatchPresenter
import com.solitelab.footballmatchschedule.data.mvp.match.MatchView
import com.solitelab.footballmatchschedule.data.mvp.model.League
import com.solitelab.footballmatchschedule.data.mvp.model.LeagueDetail
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_match.*
import kotlinx.android.synthetic.main.appbar.*
import org.jetbrains.anko.defaultSharedPreferences
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class MatchActivity : AppCompatActivity(), MatchView {
    private lateinit var presenter : MatchPresenter
    private val appActivity = this
    private var leagueDetail: LeagueDetail? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

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

        setSupportActionBar(toolbar)
        supportActionBar?.title = league?.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchPresenter(this, gson, request)
        presenter.getLeagueDetail(league?.id.toString())

        league?.image?.let {
            Picasso.get().load(it).into(img_league_logo)
        }

        tv_league_name.text = league?.name
        tv_league_alternative.text = "%s %s".format(getString(R.string.alternative), "-")
        tv_league_country.text = "%s %s".format(getString(R.string.country), "-")
        tv_league_formed.text = "%s %s".format(getString(R.string.formed), "-")
        tv_league_website.text = "%s %s".format(getString(R.string.website), "-")

        tv_league_website.onClick {
            val text = tv_league_website.text
            val arr = text.split(":")
            var website = arr[1].trim()

            if (!website.startsWith("http://") && !website.startsWith("https://")) {
                website = "http://$website"
            }

            val uris = Uri.parse(website)
            val intent = Intent(Intent.ACTION_VIEW, uris)
            startActivity(intent)
        }

        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, _ ->
            val offsetAlpha = (appBarLayout.y / appBarLayout.totalScrollRange)
            toolbar.setTitleTextColor(Color.argb(((offsetAlpha * -1) * 255).toInt(), 255, 255, 255))
        })
    }

    private fun initDeclaration() {

        val tabsTitle = ArrayList<String>()
        tabsTitle.add("LAST MATCH")
        tabsTitle.add("NEXT MATCH")

        val tabsFrament = ArrayList<Fragment>()
        tabsFrament.add(LastMatchFragment())
        tabsFrament.add(NextMatchFragment())

        val pageAdapter = MatchPageAdapter(supportFragmentManager, tabsFrament, tabsTitle)
        viewpager.adapter = pageAdapter
        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))

        tabs.setupWithViewPager(viewpager)
    }

    override fun showLeagueDetail(detail: LeagueDetail?) {
        leagueDetail = detail
        tv_league_alternative.text = "%s %s".format(
            getString(R.string.alternative),
            if (detail?.alternate!!.isNotEmpty()) detail.alternate else "-")

        tv_league_country.text = "%s %s".format(getString(R.string.country), detail.country)
        tv_league_formed.text = "%s %s".format(getString(R.string.formed), detail.formed)

        val udata = "%s %s".format(getString(R.string.website), detail.website)
        val content = SpannableString(udata)
        content.setSpan(UnderlineSpan(), 0, udata.length, 0)

        tv_league_website.text = content
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
            R.id.search_btn -> {
                startActivity<SearchActivity>()
                true
            }
            R.id.detail -> {
                if (leagueDetail != null) {
                    val options = ActivityOptions.makeSceneTransitionAnimation(
                        appActivity,
                        img_league_logo,
                        ViewCompat.getTransitionName(img_league_logo)
                    )
                    startActivity(intentFor<LeagueDetailActivity>("league" to leagueDetail), options.toBundle())
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}