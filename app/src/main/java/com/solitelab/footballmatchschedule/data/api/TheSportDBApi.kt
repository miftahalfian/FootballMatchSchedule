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

    fun searchTeam(query : String) : String {
        return  BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/searchteams.php?t=" + query
    }

    fun getTeamDetail(id : String?) : String {
        return  BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/lookupteam.php?id=" + id
    }

    fun getStandings(id: String) : String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/lookuptable.php?l=" + id
    }

    fun getTeams(id : String?) : String {
        return  BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/lookup_all_teams.php?id=" + id
    }

    fun getPlayers(id : String?) : String {
        return  BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}/lookup_all_players.php?id=" + id
    }
}