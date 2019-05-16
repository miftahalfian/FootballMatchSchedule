package com.solitelab.footballmatchschedule.ui

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.data.adapter.PageAdapter
import com.solitelab.footballmatchschedule.data.mvp.model.Team
import com.solitelab.footballmatchschedule.data.mvp.teamdetail.TeamDetailPresenter
import com.solitelab.footballmatchschedule.data.mvp.teamdetail.TeamDetailView
import com.solitelab.footballmatchschedule.ui.fragments.PlayerFragment
import com.solitelab.footballmatchschedule.ui.fragments.TeamDetailFragment
import com.solitelab.footballmatchschedule.ui.layout.TeamDetailUI
import com.squareup.picasso.Picasso
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.find
import org.jetbrains.anko.setContentView

class TeamDetailActivity : AppCompatActivity(), TeamDetailView {
    private val ui : TeamDetailUI = TeamDetailUI()
    private var menuItem: Menu? = null
    private var logoScaleX = 0.0f
    private var logoScaleY = 0.0f
    private var isFavorite = false

    lateinit var team : Team
    lateinit var presenter : TeamDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui.setContentView(this)

        team = intent.getParcelableExtra("team")

        setSupportActionBar(ui.toolbar)
        supportActionBar?.title = team.teamName
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ui.teamName.text = team.teamName
        ui.teamCountry.text = team.country
        team.badge?.let {
            Picasso.get().load(it).into(ui.teamLogo)
        }

        logoScaleX = ui.teamLogo.scaleX
        logoScaleY = ui.teamLogo.scaleY

        ui.appBar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, _ ->
            val offsetAlpha = (appBarLayout.y / appBarLayout.totalScrollRange)
            ui.toolbar.setTitleTextColor(Color.argb(((offsetAlpha * -1) * 255).toInt(), 255, 255, 255))
            ui.teamLogo.scaleX = logoScaleX * (1 - offsetAlpha * -1)
            ui.teamLogo.scaleY = logoScaleY * (1 - offsetAlpha * -1)
        })

        presenter = TeamDetailPresenter(this)
        isFavorite = presenter.isFavorite(team, this)

        initDeclaration()
    }

    private fun initDeclaration() {

        val tabsTitle = ArrayList<String>()
        tabsTitle.add("TEAM INFO")
        tabsTitle.add("PLAYERS")

        val tabsFragment = ArrayList<Fragment>()
        tabsFragment.add(TeamDetailFragment())
        tabsFragment.add(PlayerFragment())

        val pageAdapter = PageAdapter(supportFragmentManager, tabsFragment, tabsTitle)
        ui.viewPager.adapter = pageAdapter
        ui.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(ui.tabLayout))

        ui.tabLayout.setupWithViewPager(ui.viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_fav, menu)
        menuItem = menu

        setFavorite()

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                finishAfterTransition()
                true
            }
            R.id.favorite -> {
                switchFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun switchFavorite() {
        isFavorite = !isFavorite

        setFavorite()

        if (isFavorite) presenter.addToFavorite(team, this) else presenter.removeFromFavorite(team, this)
    }

    private fun setFavorite() {
        menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(
            this,
            if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border)
    }


    fun getRootView() : ViewGroup {
        return find<ViewGroup>(android.R.id.content).getChildAt(0) as ViewGroup
    }

    override fun onAddedToFavorite() {
        getRootView().snackbar("Added To Favorite").show()
    }

    override fun onRemovedFromFavorite() {
        getRootView().snackbar("Removed From Favorite").show()
    }

    override fun onShowError(message: String) {
        getRootView().snackbar(message).show()
    }

}
