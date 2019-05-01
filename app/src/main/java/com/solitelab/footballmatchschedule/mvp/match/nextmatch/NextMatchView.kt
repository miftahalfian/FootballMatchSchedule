package com.solitelab.footballmatchschedule.mvp.match.nextmatch

import com.solitelab.footballmatchschedule.mvp.model.Match

interface NextMatchView {
    fun onLoadData()
    fun onDataLoaded(matches : List<Match>)
    fun onLoadFailed()
}