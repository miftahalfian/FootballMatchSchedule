package com.solitelab.footballmatchschedule.mvp.search

import com.solitelab.footballmatchschedule.mvp.model.Match

interface SearchView {
    fun onLoadData()
    fun onDataLoaded(matches : List<Match>?)
}