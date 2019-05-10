package com.solitelab.footballmatchschedule.ui.layout

import android.support.v7.widget.RecyclerView
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.ui.MainActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainUI : AnkoComponent<MainActivity> {
    lateinit var leagueList: RecyclerView

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        verticalLayout{
            padding = dip(16)
            lparams(matchParent, matchParent)

            leagueList = recyclerView {
                id = R.id.league_list
                lparams(matchParent, wrapContent)
            }
        }
    }
}