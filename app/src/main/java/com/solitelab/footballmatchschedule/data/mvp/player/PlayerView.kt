package com.solitelab.footballmatchschedule.data.mvp.player

import com.solitelab.footballmatchschedule.data.mvp.model.Player

interface PlayerView {
    fun onLoadData()
    fun onDataLoaded(player: List<Player>)
    fun onLoadFailed()
}