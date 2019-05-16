package com.solitelab.footballmatchschedule.data.mvp.standing

import com.solitelab.footballmatchschedule.data.mvp.model.Standing

interface StandingView {
    fun onLoadData()
    fun onDataLoaded(data: List<Standing>)
}