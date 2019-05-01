package com.solitelab.footballmatchschedule.data.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.gson.Gson
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.api.TheSportDBApi
import com.solitelab.footballmatchschedule.data.mvp.model.Match
import com.solitelab.footballmatchschedule.data.mvp.model.TeamResult
import com.solitelab.footballmatchschedule.utils.formatTo
import com.solitelab.footballmatchschedule.utils.toDate
import com.squareup.picasso.Picasso
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread

class MatchAdapter(private val listener: (match:Match, homeLogo:ImageView, awayLogo:ImageView, homeSrc:String, awaySrc:String) -> Unit)
    : RecyclerView.Adapter<MatchAdapter.ViewHolder>() {

    private var items: ArrayList<Match> = ArrayList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvLeagueName : TextView = itemView.find(R.id.tv_league_name)
        private val tvDate : TextView = itemView.find(R.id.tv_date)
        private val tvTime : TextView = itemView.find(R.id.tv_time)
        private val homeLogo : ImageView = itemView.find(R.id.img_home_logo)
        private val awayLogo : ImageView = itemView.find(R.id.img_away_logo)
        private val homeScore : TextView = itemView.find(R.id.tv_home_score)
        private val awayScore : TextView = itemView.find(R.id.tv_away_score)
        private val tvHomeName : TextView = itemView.find(R.id.tv_home_team_name)
        private val tvAwayName : TextView = itemView.find(R.id.tv_away_team_name)

        private val request = ApiRepository()
        private val gson = Gson()

        private var homeImageSrc : String = ""
        private var awayImageSrc : String = ""

        fun bind(match : Match, listener: (match:Match,homeLogo:ImageView,awayLogo:ImageView,homeSrc:String, awaySrc:String) -> Unit) {
            tvLeagueName.text = match.league
            tvDate.text = match.date!!.toDate("yyyy-MM-dd").formatTo("EEEE, dd MMMM, yyy")
            tvTime.text = match.time!!.toDate("HH:mm:ss").formatTo("HH:mm:ss")
            homeScore.text = if (match.homeScore == null) "-" else match.homeScore.toString()
            awayScore.text = if (match.awayScore == null) "-" else match.awayScore.toString()
            tvHomeName.text = match.homeTeamName
            tvAwayName.text = match.awayTeamName

            loadTeamBadge(match.homeTeamID, homeLogo) {
                homeImageSrc = it!!
            }
            loadTeamBadge(match.awayTeamID, awayLogo) {
                awayImageSrc = it!!
            }

            itemView.setOnClickListener {
                listener(match, homeLogo, awayLogo, homeImageSrc, awayImageSrc)
            }
        }

        fun loadTeamBadge(id : String?, img : ImageView, listener: (String?) -> Unit) {
            doAsync {
                val data = gson.fromJson(request
                    .doRequest(TheSportDBApi.getTeamDetail(id)),
                    TeamResult::class.java
                )

                uiThread {
                    if (data.teams[0].badge != null) {
                        Log.d("match adapter", data.teams[0].badge)
                        Picasso.get().load(data.teams[0].badge).into(img)
                        listener(data.teams[0].badge)
                    }
                }
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
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.match_items_row, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

}