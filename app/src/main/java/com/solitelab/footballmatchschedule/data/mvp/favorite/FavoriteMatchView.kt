package com.solitelab.footballmatchschedule.data.mvp.favorite

import com.solitelab.footballmatchschedule.data.mvp.model.Match

interface FavoriteMatchView {
    fun onLoadData()
    fun onDataLoaded(matches : List<Match>)
}