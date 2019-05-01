package com.solitelab.footballmatchschedule.data.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.solitelab.footballmatchschedule.ui.layout.LeagueItemUI
import com.solitelab.footballmatchschedule.data.mvp.model.League
import com.squareup.picasso.Picasso
import org.jetbrains.anko.AnkoContext

class LeagueAdapter(private val items: List<League>,
                    private val listener: (Pair<League, View>) -> Unit) : RecyclerView.Adapter<LeagueAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var imgPoster: ImageView = itemView.findViewById(LeagueItemUI.imgId)
        private var tvName: TextView = itemView.findViewById(LeagueItemUI.tvNameId)

        fun bind(league: League, listener: (Pair<League, View>) -> Unit) {
            tvName.text = league.name
            league.image?.let { Picasso.get().load(it).into(imgPoster) }
            itemView.setOnClickListener {
                listener(Pair(league, imgPoster))
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LeagueItemUI().createView(AnkoContext.create(parent.context, parent)))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }
}