package com.solitelab.footballmatchschedule.data.mvp.leaguedetail

import com.solitelab.footballmatchschedule.data.mvp.model.LeagueDetail

interface LeagueDetailView {
    fun showLeagueDetail(detail: LeagueDetail?)
}