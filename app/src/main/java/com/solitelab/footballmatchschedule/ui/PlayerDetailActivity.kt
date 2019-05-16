package com.solitelab.footballmatchschedule.ui

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.text.Html
import android.view.MenuItem
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.data.mvp.model.Player
import com.solitelab.footballmatchschedule.utils.formatTo
import com.solitelab.footballmatchschedule.utils.toDate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player_detail.*
import java.util.*

class PlayerDetailActivity : AppCompatActivity() {
    var iconScaleX = 0.0f
    var iconScaleY = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        setSupportActionBar(toolbar)

        val player : Player = intent.getParcelableExtra("player")

        supportActionBar?.title = player.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        player.thumb?.let {
            Picasso.get().load(it).into(player_icon)
        }

        player_name.text = Html.fromHtml(player.name)
        player_position.text = player.position

        player.dateBorn?.let {
            val date = it.toDate("yyyy-MM-dd")
            val date_now = Date()

            val diff = date_now.time - date.time
            val day_diff = diff / (1000 * 3600 * 24) //
            val year_diff = day_diff / 365
            age.text = "%s (%s)".format(year_diff.toString(), date.formatTo("dd/MM/yyyy"))
        }

        nationality.text = player.nationality
        birthplace.text = player.birthLocation
        team_name.text = player.team
        height.text = if (player.height.isNullOrEmpty()) "-" else player.height
        weight.text = if (player.weight.isNullOrEmpty()) "-" else player.weight
        description.text = player.description

        iconScaleX = player_icon.scaleX
        iconScaleY = player_icon.scaleY

        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, _ ->
            val offsetAlpha = (appBarLayout.y / appBarLayout.totalScrollRange)
            toolbar.setTitleTextColor(Color.argb(((offsetAlpha * -1) * 255).toInt(), 255, 255, 255))

            player_icon.scaleX = iconScaleX * (1 - offsetAlpha * -1)
            player_icon.scaleY = iconScaleY * (1 - offsetAlpha * -1)
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
