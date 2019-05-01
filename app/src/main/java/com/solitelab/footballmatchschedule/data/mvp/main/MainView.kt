package com.solitelab.footballmatchschedule.data.mvp.main

import com.solitelab.footballmatchschedule.data.mvp.model.League

interface MainView {
    fun setData(items: MutableList<League>)
}