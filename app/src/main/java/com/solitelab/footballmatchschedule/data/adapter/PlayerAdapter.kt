package com.solitelab.footballmatchschedule.data.adapter

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.data.mvp.model.Player
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

class PlayerAdapter(val listener: (icon:ImageView, player:Player) -> Unit): RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    private var items: ArrayList<Player> = ArrayList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val playerIcon : ImageView = itemView.find(R.id.player_icon)
        private val playerName : TextView = itemView.find(R.id.player_name)
        private val teamName : TextView = itemView.find(R.id.team_name)
        private val playerPosition : TextView = itemView.find(R.id.player_position)
        
        fun bind(player: Player, listener: (ImageView, Player) -> Unit) {
            playerName.text = Html.fromHtml(player.name)
            teamName.text = player.team
            playerPosition.text = player.position
            player.thumb?.let {
                Picasso.get().load(it).into(playerIcon)
            }

            itemView.onClick {
                listener(playerIcon, player)
            }
        }
    }

    fun setData(newItems: List<Player>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.player_items_row, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }
}