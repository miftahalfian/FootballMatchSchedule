package com.solitelab.footballmatchschedule.data.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.data.mvp.model.Match
import com.solitelab.footballmatchschedule.ui.layout.MatchItemUI
import com.solitelab.footballmatchschedule.utils.formatTo
import com.solitelab.footballmatchschedule.utils.loadTeamBadge
import com.solitelab.footballmatchschedule.utils.toDate
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class MatchAdapter(private val listener: (match:Match, homeLogo:ImageView, awayLogo:ImageView, homeSrc:String, awaySrc:String) -> Unit)
    : RecyclerView.Adapter<MatchAdapter.ViewHolder>() {

    private var items: ArrayList<Match> = ArrayList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvLeagueName : TextView = itemView.find(R.id.league_name)
        private val tvDate : TextView = itemView.find(R.id.match_date)
        private val tvTime : TextView = itemView.find(R.id.match_time)
        private val homeLogo : ImageView = itemView.find(R.id.home_logo)
        private val awayLogo : ImageView = itemView.find(R.id.away_logo)
        private val homeScore : TextView = itemView.find(R.id.home_score)
        private val awayScore : TextView = itemView.find(R.id.away_score)
        private val tvHomeName : TextView = itemView.find(R.id.home_team_name)
        private val tvAwayName : TextView = itemView.find(R.id.away_team_name)

        private var homeImageSrc : String = ""
        private var awayImageSrc : String = ""

        fun bind(match : Match, listener: (match:Match,homeLogo:ImageView,awayLogo:ImageView,homeSrc:String, awaySrc:String) -> Unit) {
            tvLeagueName.text = match.league
            tvDate.text = match.date!!.toDate("yyyy-MM-dd").formatTo("EEEE, dd MMMM, yyy")

            Log.d("Match Adapter", match.time)
            if (match.time!!.length <= 9) {
                tvTime.text = match.time
            }
            else tvTime.text = match.time.toDate("HH:mm:ssz").formatTo("HH:mm:ss")


            homeScore.text = if (match.homeScore == null) "-" else match.homeScore.toString()
            awayScore.text = if (match.awayScore == null) "-" else match.awayScore.toString()
            tvHomeName.text = match.homeTeamName
            tvAwayName.text = match.awayTeamName

            loadTeamBadge(match.homeTeamID) {
                homeImageSrc = it!!
                homeImageSrc.let { it1 ->
                    Picasso.get().load(it1).into(homeLogo)
                }
            }
            loadTeamBadge(match.awayTeamID) {
                awayImageSrc = it!!
                awayImageSrc.let { it1 ->
                    Picasso.get().load(it1).into(awayLogo)
                }
            }

            itemView.setOnClickListener {
                listener(match, homeLogo, awayLogo, homeImageSrc, awayImageSrc)
            }
        }

    }

    fun setData(newItems: List<Match>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(MatchItemUI().createView(AnkoContext.create(parent.context, parent)))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

}