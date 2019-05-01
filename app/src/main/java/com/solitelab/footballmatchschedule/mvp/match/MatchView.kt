package com.solitelab.footballmatchschedule.mvp.match

import com.solitelab.footballmatchschedule.mvp.model.LeagueDetail

interface MatchView {
    fun showLeagueDetail(detail: LeagueDetail)
}