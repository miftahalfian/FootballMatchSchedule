package com.solitelab.footballmatchschedule.data.mvp.match.lastmatch

import com.google.gson.Gson
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.api.TheSportDBApi
import com.solitelab.footballmatchschedule.data.mvp.model.MatchResult
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LastMatchPresenter(val view : LastMatchView, val gson: Gson, private val apiRepository: ApiRepository) {
    fun getLastMatch(leagueID : Int?) {
        view.onLoadData()
        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getLastMatch(leagueID.toString())),
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