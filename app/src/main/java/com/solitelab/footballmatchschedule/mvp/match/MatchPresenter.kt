package com.solitelab.footballmatchschedule.mvp.match

import com.google.gson.Gson
import com.solitelab.footballmatchschedule.api.ApiRepository
import com.solitelab.footballmatchschedule.api.TheSportDBApi
import com.solitelab.footballmatchschedule.mvp.model.LeagueDetail
import com.solitelab.footballmatchschedule.mvp.model.LeagueResult
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchPresenter (val view : MatchView, val gson: Gson, val apiRepository: ApiRepository) {
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