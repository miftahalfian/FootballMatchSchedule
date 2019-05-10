package com.solitelab.footballmatchschedule.data.mvp.match.nextmatch

import com.google.gson.Gson
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.api.TheSportDBApi
import com.solitelab.footballmatchschedule.data.mvp.model.MatchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NextMatchPresenter(val view : NextMatchView,
                         val gson: Gson,
                         private val apiRepository: ApiRepository
) {
    fun getNextMatch(leagueID : Int?) {
        view.onLoadData()
        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNextMatch(leagueID.toString())).await(),
                MatchResult::class.java
            )

            data.events?.let {
                view.onDataLoaded(it)
            } ?: view.onLoadFailed()
        }
    }
}