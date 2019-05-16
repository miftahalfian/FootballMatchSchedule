package com.solitelab.footballmatchschedule.data.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.data.mvp.model.Standing
import com.solitelab.footballmatchschedule.utils.loadTeamBadge
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

class StandingAdapter: RecyclerView.Adapter<StandingAdapter.ViewHolder>() {

    private var items: ArrayList<Standing> = ArrayList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val teamLogo : ImageView = itemView.find(R.id.team_logo)
        private val teamName : TextView = itemView.find(R.id.team_name)
        private val played : TextView = itemView.find(R.id.played)
        private val win : TextView = itemView.find(R.id.win)
        private val draw : TextView = itemView.find(R.id.draw)
        private val loss : TextView = itemView.find(R.id.loss)
        private val total : TextView = itemView.find(R.id.total)
        private val no : TextView = itemView.find(R.id.no)

        companion object {
            private var teamBadgeMap: MutableMap<String, String> = mutableMapOf()
        }

        fun bind(standing: Standing, position: Int) {
            Log.d("Standing Adapter", "Team ID: ${standing.teamId}")

            loadTeamBadge(standing.teamId) {
                Picasso.get().load(it).into(teamLogo)
            }

            if (teamBadgeMap.containsKey(standing.teamId)) {
                Picasso.get().load(teamBadgeMap[standing.teamId]).into(teamLogo)
            }
            else {
                loadTeamBadge(standing.teamId) {it1 ->
                    it1?.let {
                        teamBadgeMap.put(standing.teamId!!, it)
                        Picasso.get().load(it).into(teamLogo)
                    }
                }
            }

            teamName.text = standing.name
            played.text = standing.played.toString()
            win.text = standing.win.toString()
            draw.text = standing.draw.toString()
            loss.text = standing.loss.toString()
            total.text = standing.total.toString()
            no.text = (position + 1).toString()
        }
    }

    fun setData(newItems: List<Standing>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.standing_items_row, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }
}