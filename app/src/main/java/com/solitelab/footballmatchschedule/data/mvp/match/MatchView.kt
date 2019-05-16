package com.solitelab.footballmatchschedule.data.mvp.match

import com.solitelab.footballmatchschedule.data.mvp.model.Match

interface MatchView {
    fun onLoadData()
    fun onLoadFailed(type: String)
    fun onDataLoaded(data: List<Match>, type: String)
}