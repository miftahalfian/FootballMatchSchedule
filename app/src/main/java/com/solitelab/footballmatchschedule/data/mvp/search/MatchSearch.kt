package com.solitelab.footballmatchschedule.data.mvp.search

import com.solitelab.footballmatchschedule.data.mvp.model.Match

interface MatchSearch {
    fun onLoadData()
    fun onDataLoaded(match: List<Match>)
    fun onLoadFailed()
}