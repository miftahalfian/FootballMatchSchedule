package com.solitelab.footballmatchschedule.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.data.mvp.matchdetail.MatchDetailPresenter
import com.solitelab.footballmatchschedule.data.mvp.matchdetail.MatchDetailView
import com.solitelab.footballmatchschedule.data.mvp.model.Match
import com.solitelab.footballmatchschedule.utils.MatchEvent
import com.solitelab.footballmatchschedule.utils.formatTo
import com.solitelab.footballmatchschedule.utils.toDate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_match_detail.*
import kotlinx.android.synthetic.main.content_match_detail.*
import kotlinx.android.synthetic.main.lineup_layout.*
import kotlinx.android.synthetic.main.match_appbar.*
import org.jetbrains.anko.find

class MatchDetailActivity : AppCompatActivity(), MatchDetailView {
    var match : Match? = null
    private val presenter : MatchDetailPresenter = MatchDetailPresenter(this)
    private var isFavorite = false
    private var menuItem: Menu? = null

    var homeBigScaleX = 0.0f
    var homeBigScaleY = 0.0f
    var awayBigScaleY = 0.0f
    var awayBigScaleX = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)
        setSupportActionBar(toolbar)

        match = intent.getParcelableExtra("match")
        val homeSrc = intent.getStringExtra("homeSrc")
        val awaySrc = intent.getStringExtra("awaySrc")

        supportActionBar?.title = "%s %s : %s %s".format(
            match?.homeTeamName,
            if (match?.homeScore != null) match?.homeScore.toString() else "-",
            if (match?.awayScore != null) match?.awayScore.toString() else "-",
            match?.awayTeamName
        )

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bigHomeName.text = match?.homeTeamName
        bigAwayName.text = match?.awayTeamName
        smallHomeName.text = match?.homeTeamName
        smallAwayName.text = match?.awayTeamName

        leagueName.text = match?.league

        val date = match?.date!!.toDate("yyyy-MM-dd").formatTo("EEEE, dd MMMM, yyy")
        val time = match?.time!!.toDate("HH:mm:ss").formatTo("HH:mm:ss")

        val dateString = "%s\n%s".format(date, time)
        leagueDate.text = dateString

        tvScore.text = "%s : %s".format(
            if (match?.homeScore != null) match?.homeScore.toString() else "-",
            if (match?.awayScore != null) match?.awayScore.toString() else "-"
        )

        if (!homeSrc.isNullOrEmpty()) {
            Picasso.get().load(homeSrc).into(homeBigLogo)
            Picasso.get().load(homeSrc).into(smallHomeLogo)
        }
        else {
            presenter.loadTeamBadge(match?.homeTeamID, "home")
        }

        if (!awaySrc.isNullOrEmpty()) {
            Picasso.get().load(awaySrc).into(awayBigLogo)
            Picasso.get().load(awaySrc).into(smallAwayLogo)
        }
        else {
            presenter.loadTeamBadge(match?.awayTeamID, "away")
        }

        homeGoalKeeper.text = match?.homeGoalKeeper?.replace(";", ",\n")?.trim()
        homeDefense.text = match?.homeDefence?.replace(";", ",\n")?.trim()
        homeMidfield.text = match?.homeMidfield?.replace(";", ",\n")?.trim()
        homeForward.text = match?.homeForward?.replace(";", ",\n")?.trim()
        homeSubstitute.text = match?.homeSubstitutes?.replace(";", ",\n")?.trim()

        awayGoalKeeper.text = match?.awayGoalKeeper?.replace(";", ",\n")?.trim()
        awayDefense.text = match?.awayDefence?.replace(";", ",\n")?.trim()
        awayMidfield.text = match?.awayMidfield?.replace(";", ",\n")?.trim()
        awayForward.text = match?.awayForward?.replace(";", ",\n")?.trim()
        awaySubstitute.text = match?.awaySubstitutes?.replace(";", ",\n")?.trim()

        val linearLayout = match_summary as LinearLayout

        val events = ArrayList<MatchEvent>()

        if (match?.homeGoalDetail != null) {
            if (match?.homeGoalDetail!!.isNotEmpty()) {
                val homeGoals = match?.homeGoalDetail!!.split(';')
                for(i in homeGoals.indices) {
                    if (homeGoals[i].isNotEmpty()) {
                        val event = MatchEvent()
                        event.parse(homeGoals[i].trim(), MatchEvent.EventType.SHOT, MatchEvent.TeamType.HOME)
                        events.add(event)
                    }
                }
            }
        }

        if (match?.awayGoalDetail != null) {
            if (match?.awayGoalDetail!!.isNotEmpty()) {
                val awayGoals = match?.awayGoalDetail!!.split(';')
                for(i in awayGoals.indices) {
                    if (awayGoals[i].isNotEmpty()) {
                        val event = MatchEvent()
                        event.parse(awayGoals[i].trim(), MatchEvent.EventType.SHOT, MatchEvent.TeamType.AWAY)
                        events.add(event)
                    }
                }
            }
        }

        if (match?.homeRedCards != null) {
            if (match?.homeRedCards!!.isNotEmpty()) {
                val homeRedCards = match?.homeRedCards!!.split(';')
                for(i in homeRedCards.indices) {
                    if (homeRedCards[i].isNotEmpty()) {
                        val event = MatchEvent()
                        event.parse(homeRedCards[i], MatchEvent.EventType.RED_CARD, MatchEvent.TeamType.HOME)
                        events.add(event)
                    }
                }
            }
        }

        if (match?.awayRedCards != null) {
            if (match?.awayRedCards!!.isNotEmpty()) {
                val awayRedCards = match?.awayRedCards!!.split(';')
                for(i in awayRedCards.indices) {
                    if (awayRedCards[i].isNotEmpty()) {
                        val event = MatchEvent()
                        event.parse(awayRedCards[i], MatchEvent.EventType.RED_CARD, MatchEvent.TeamType.AWAY)
                        events.add(event)
                    }
                }
            }
        }

        if (match?.homeYellowCards != null) {
            if (match?.homeYellowCards!!.isNotEmpty()) {
                val homeYellowCards = match?.homeYellowCards!!.split(';')
                for(i in homeYellowCards.indices) {
                    if (homeYellowCards[i].isNotEmpty()) {
                        val event = MatchEvent()
                        event.parse(homeYellowCards[i], MatchEvent.EventType.RED_CARD, MatchEvent.TeamType.HOME)
                        events.add(event)
                    }
                }
            }
        }

        if (match?.awayYellowCards != null) {
            if (match?.awayYellowCards!!.isNotEmpty()) {
                val awayYellowCards = match?.awayYellowCards!!.split(';')
                for(i in awayYellowCards.indices) {
                    if (awayYellowCards[i].isNotEmpty()) {
                        val event = MatchEvent()
                        event.parse(awayYellowCards[i], MatchEvent.EventType.YELLOW_CARD, MatchEvent.TeamType.AWAY)
                        events.add(event)
                    }
                }
            }
        }

        val sortedEvent = events.sortedBy {it.time}

        for(i in sortedEvent.indices) {
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            if (sortedEvent[i].team == MatchEvent.TeamType.HOME) {
                val view = inflater.inflate(R.layout.item_summary_home, null)
                linearLayout.addView(view)

                val matchTime = view.find<TextView>(R.id.matchTime)
                matchTime.text = "%s'".format(sortedEvent[i].time.toString())

                val playerName = view.find<TextView>(R.id.playersName)
                playerName.text =sortedEvent[i].player

                val icon = view.find<ImageView>(R.id.imgLogo)
                when(sortedEvent[i].type) {
                    MatchEvent.EventType.SHOT -> Picasso.get().load(R.drawable.soccer_icon).into(icon)
                    MatchEvent.EventType.RED_CARD -> Picasso.get().load(R.drawable.red_card).into(icon)
                    else -> Picasso.get().load(R.drawable.yellow_card).into(icon)
                }

            }
            else if (sortedEvent[i].team == MatchEvent.TeamType.AWAY) {
                val view = inflater.inflate(R.layout.item_summary_away, null)
                linearLayout.addView(view)

                val matchTime = view.find<TextView>(R.id.matchTime)
                matchTime.text = "%s'".format(sortedEvent[i].time.toString())

                val playerName = view.find<TextView>(R.id.playersName)
                playerName.text =sortedEvent[i].player

                val icon = view.find<ImageView>(R.id.imgLogo)
                when(sortedEvent[i].type) {
                    MatchEvent.EventType.SHOT -> Picasso.get().load(R.drawable.soccer_icon).into(icon)
                    MatchEvent.EventType.RED_CARD -> Picasso.get().load(R.drawable.red_card).into(icon)
                    else -> Picasso.get().load(R.drawable.yellow_card).into(icon)
                }
            }
        }

        homeBigScaleX = homeBigLogo.scaleX
        homeBigScaleY = homeBigLogo.scaleY
        awayBigScaleX = awayBigLogo.scaleX
        awayBigScaleY = awayBigLogo.scaleY

        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, _ ->
            val offsetAlpha = (appBarLayout.y / appBarLayout.totalScrollRange)
            toolbar.setTitleTextColor(Color.argb(((offsetAlpha * -1) * 255).toInt(), 255, 255, 255))
            homeBigLogo.scaleX = homeBigScaleX * (1 - offsetAlpha * -1)
            homeBigLogo.scaleY = homeBigScaleY * (1 - offsetAlpha * -1)
            awayBigLogo.scaleX = awayBigScaleX * (1 - offsetAlpha * -1)
            awayBigLogo.scaleY = awayBigScaleY * (1 - offsetAlpha * -1)
        })

        isFavorite = presenter.isFavorite(match)

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

    override fun onBadgeLoaded(imgSrc: String?, tag: String) {
        when(tag) {
            "home" -> {
                Picasso.get().load(imgSrc).into(homeBigLogo)
                Picasso.get().load(imgSrc).into(smallHomeLogo)
            }
            "away" -> {
                Picasso.get().load(imgSrc).into(awayBigLogo)
                Picasso.get().load(imgSrc).into(smallAwayLogo)
            }
        }
    }

    private fun switchFavorite() {
        isFavorite = !isFavorite

        setFavorite()

        if (isFavorite) presenter.addToFavorite(match) else presenter.removeFromFavorite(match)
    }

    private fun setFavorite() {
        menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(
            this,
            if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border)
    }

    fun getRootView() : ViewGroup {
        return find<ViewGroup>(android.R.id.content).getChildAt(0) as ViewGroup
    }

}
