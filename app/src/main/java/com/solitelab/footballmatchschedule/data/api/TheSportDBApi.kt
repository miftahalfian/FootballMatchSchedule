package com.solitelab.footballmatchschedule.data.api

import com.solitelab.footballmatchschedule.BuildConfig

object TheSportDBApi {
    fun getLeagueDetail(id: String?): String {
        return  BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/lookupleague.php?id=" + id
    }

    fun getNextMatch(id : String?) : String {
        return  BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/eventsnextleague.php?id=" + id
    }

    fun getLastMatch(id : String?) : String {
        return  BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/eventspastleague.php?id=" + id
    }

    fun searchMatch(query : String) : String {
        return  BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/searchevents.php?e=" + query
    }

    fun getTeamDetail(id : String?) : String {
        return  BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/lookupteam.php?id=" + id
    }
}