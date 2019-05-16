package com.solitelab.footballmatchschedule.data.mvp.search

import com.solitelab.footballmatchschedule.data.mvp.model.Team

interface TeamSearch {
    fun onLoadData()
    fun onDataLoaded(team: List<Team>)
    fun onLoadFailed()
}