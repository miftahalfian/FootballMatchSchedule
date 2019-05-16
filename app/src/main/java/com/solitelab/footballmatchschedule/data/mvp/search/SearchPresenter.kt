package com.solitelab.footballmatchschedule.data.mvp.search

import com.google.gson.Gson
import com.solitelab.footballmatchschedule.utils.CoroutineContextProvider
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.api.TheSportDBApi
import com.solitelab.footballmatchschedule.data.mvp.model.MatchResult
import com.solitelab.footballmatchschedule.data.mvp.model.SearchResult
import com.solitelab.footballmatchschedule.data.mvp.model.TeamResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchPresenter(val matchView: MatchSearch,
                      val teamView: TeamSearch,
                      private val gson: Gson = Gson(),
                      private val apiRepository: ApiRepository = ApiRepository(),
                      private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    var query = ""

    fun search(query: String?) {
        if (query.isNullOrEmpty()) return

        searchMatch(query)
        searchTeam(query)

        this.query = query
    }

    fun refreshSearch() {
        search(query)
    }

    fun searchMatch(query: String) {
        val encodedQuery = query.replace(" ", "_")

        matchView.onLoadData()
        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.searchMatch(encodedQuery)).await(),
                SearchResult::class.java
            )

            data.event?.let {
                matchView.onDataLoaded(it)
            } ?: matchView.onLoadFailed()
        }
    }

    fun searchTeam(query: String) {
        teamView.onLoadData()
        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.searchTeam(query)).await(),
                TeamResult::class.java
            )

            data.teams?.let {
                teamView.onDataLoaded(it)
            } ?: teamView.onLoadFailed()
        }
    }
}