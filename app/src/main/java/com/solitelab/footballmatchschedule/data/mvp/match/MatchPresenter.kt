package com.solitelab.footballmatchschedule.data.mvp.match

import com.google.gson.Gson
import com.solitelab.footballmatchschedule.utils.CoroutineContextProvider
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.api.TheSportDBApi
import com.solitelab.footballmatchschedule.data.mvp.model.LeagueResult
import com.solitelab.footballmatchschedule.data.mvp.model.MatchResult
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchPresenter (
    val view : MatchView,
    val gson: Gson,
    private val apiRepository: ApiRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()

) {

    fun getLastMatch(leagueID : Int?) {
        view.onLoadData()
        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getLastMatch(leagueID.toString())).await(),
                MatchResult::class.java
            )

            data.events?.let {
                view.onDataLoaded(it, "LastMatch")
            } ?: view.onLoadFailed("LastMatch")
        }
    }

    fun getNextMatch(leagueID : Int?) {
        view.onLoadData()
        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNextMatch(leagueID.toString())).await(),
                MatchResult::class.java
            )

            data.events?.let {
                view.onDataLoaded(it, "NextMatch")
            } ?: view.onLoadFailed("NextMatch")
        }
    }
}