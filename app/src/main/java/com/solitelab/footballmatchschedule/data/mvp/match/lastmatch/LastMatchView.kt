package com.solitelab.footballmatchschedule.data.mvp.match.lastmatch

import com.solitelab.footballmatchschedule.data.mvp.model.Match

interface LastMatchView {
    fun onLoadData()
    fun onDataLoaded(matches : List<Match>)
    fun onLoadFailed()
}