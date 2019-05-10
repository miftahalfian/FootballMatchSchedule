package com.solitelab.footballmatchschedule.ui.layout

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.solitelab.footballmatchschedule.R
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.wrapContent

class MatchList : AnkoComponent<Context> {
    lateinit var recyclerView : RecyclerView

    override fun createView(ui: AnkoContext<Context>) = with(ui) {

        swipeRefreshLayout {
            setColorSchemeResources(
                R.color.colorAccent,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)

            recyclerView = recyclerView {
                id = R.id.match_list
                lparams (width = matchParent, height = wrapContent)
                layoutManager = LinearLayoutManager(context)
            }
        }
    }
}