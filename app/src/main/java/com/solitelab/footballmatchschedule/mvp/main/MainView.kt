package com.solitelab.footballmatchschedule.mvp.main

import com.solitelab.footballmatchschedule.mvp.model.League

interface MainView {
    fun setData(items: MutableList<League>)
}