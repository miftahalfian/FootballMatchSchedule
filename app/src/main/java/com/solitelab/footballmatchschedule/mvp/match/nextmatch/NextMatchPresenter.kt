package com.solitelab.footballmatchschedule.mvp.match.nextmatch

import com.google.gson.Gson
import com.solitelab.footballmatchschedule.api.ApiRepository
import com.solitelab.footballmatchschedule.api.TheSportDBApi
import com.solitelab.footballmatchschedule.mvp.model.MatchResult
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class NextMatchPresenter(val view : NextMatchView, val gson: Gson, val apiRepository: ApiRepository) {
    fun getNextMatch(leagueID : Int?) {
        view.onLoadData()
        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNextMatch(leagueID.toString())),
                MatchResult::class.java
            )

            uiThread {
                if (data.events != null) {
                    view.onDataLoaded(data.events!!)
                }
                else view.onLoadFailed()
            }
        }
    }
}