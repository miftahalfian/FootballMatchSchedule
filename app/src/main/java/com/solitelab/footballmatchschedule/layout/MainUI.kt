package com.solitelab.footballleague.layout

import android.support.v7.widget.RecyclerView
import com.solitelab.footballmatchschedule.MainActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainUI : AnkoComponent<MainActivity> {
    lateinit var leagueList: RecyclerView

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        verticalLayout{
            padding = dip(16)
            lparams(matchParent, matchParent)

            leagueList = recyclerView {
                lparams(matchParent, wrapContent)
            }
        }
    }
}