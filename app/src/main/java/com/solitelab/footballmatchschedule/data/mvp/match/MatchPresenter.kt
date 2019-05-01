package com.solitelab.footballmatchschedule.data.mvp.match

import com.google.gson.Gson
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.api.TheSportDBApi
import com.solitelab.footballmatchschedule.data.mvp.model.LeagueResult
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchPresenter (val view : MatchView, val gson: Gson, private val apiRepository: ApiRepository) {
    fun getLeagueDetail(id : String?) {
        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getLeagueDetail(id)),
                LeagueResult::class.java
            )

            uiThread {
                view.showLeagueDetail(data.leagues[0])
            }
        }
    }
}