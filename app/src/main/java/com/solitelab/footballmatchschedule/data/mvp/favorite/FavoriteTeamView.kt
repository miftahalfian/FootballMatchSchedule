package com.solitelab.footballmatchschedule.data.mvp.favorite

import com.solitelab.footballmatchschedule.data.mvp.model.Team

interface FavoriteTeamView {
    fun onLoadData()
    fun onDataLoaded(data: List<Team>)
}