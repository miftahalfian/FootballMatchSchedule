package com.solitelab.footballmatchschedule.mvp.match.lastmatch

import com.solitelab.footballmatchschedule.mvp.model.Match

interface LastMatchView {
    fun onLoadData()
    fun onDataLoaded(matches : List<Match>)
    fun onLoadFailed()
}