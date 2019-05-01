package com.solitelab.footballmatchschedule.data.mvp.match

import com.solitelab.footballmatchschedule.data.mvp.model.LeagueDetail

interface MatchView {
    fun showLeagueDetail(detail: LeagueDetail?)
}