package com.solitelab.footballmatchschedule.data.mvp.team

import com.solitelab.footballmatchschedule.data.mvp.model.Team

interface TeamView {
    fun onLoadData()
    fun onDataLoaded(data: List<Team>)
    fun onLoadFailed()
}