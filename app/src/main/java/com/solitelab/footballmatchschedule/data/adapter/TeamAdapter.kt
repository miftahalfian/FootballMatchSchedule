package com.solitelab.footballmatchschedule.data.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.data.mvp.model.Standing
import com.solitelab.footballmatchschedule.data.mvp.model.Team
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

class TeamAdapter(private val listener: (team:Team) -> Unit): RecyclerView.Adapter<TeamAdapter.ViewHolder>() {

    private var items: ArrayList<Team> = ArrayList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val logo: ImageView = itemView.find(R.id.logo)
        val teamName: TextView = itemView.find(R.id.team_name)
        val stadium: TextView = itemView.find(R.id.stadium)
        val country: TextView = itemView.find(R.id.country)

        fun bind(data: Team, listener: (team:Team) -> Unit) {
            Picasso.get().load(data.badge).into(logo)
            teamName.text = data.teamName
            stadium.text = data.stadium
            country.text = data.country
            itemView.setOnClickListener {
                listener(data)
            }
        }
    }

    fun setData(newItems: List<Team>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.team_items_row,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }
}