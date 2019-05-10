package com.solitelab.footballmatchschedule.ui.layout

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

class MatchListUI : AnkoComponent<Context> {
    lateinit var swipeContainer: SwipeRefreshLayout
    lateinit var matchList: RecyclerView
    lateinit var noResult: LinearLayout

    override fun createView(ui: AnkoContext<Context>) : View {
        val matchListUI = MatchList()
        val noResultUI = NoResult()

        val layout = ui.relativeLayout {
            lparams(matchParent, matchParent)

            swipeContainer = ankoView({ matchListUI.createView(AnkoContext.create(it)) }, 0) {
            }

            noResult = ankoView({ noResultUI.createView(AnkoContext.create(it)) }, 0) {
            }.lparams(wrapContent, wrapContent) {
                centerHorizontally()
                centerVertically()
            }

        }

        matchList = matchListUI.recyclerView

        return layout
    }
}