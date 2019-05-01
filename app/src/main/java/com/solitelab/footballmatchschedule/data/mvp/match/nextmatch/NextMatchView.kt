package com.solitelab.footballmatchschedule.data.mvp.match.nextmatch

import com.solitelab.footballmatchschedule.data.mvp.model.Match

interface NextMatchView {
    fun onLoadData()
    fun onDataLoaded(matches : List<Match>)
    fun onLoadFailed()
}