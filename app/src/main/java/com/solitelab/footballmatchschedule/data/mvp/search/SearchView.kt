package com.solitelab.footballmatchschedule.data.mvp.search

import com.solitelab.footballmatchschedule.data.mvp.model.Match

interface SearchView {
    fun onLoadData()
    fun onDataLoaded(matches : List<Match>?)
}