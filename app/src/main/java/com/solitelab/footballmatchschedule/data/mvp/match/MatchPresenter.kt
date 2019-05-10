package com.solitelab.footballmatchschedule.data.mvp.match

import com.google.gson.Gson
import com.solitelab.footballmatchschedule.CoroutineContextProvider
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.api.TheSportDBApi
import com.solitelab.footballmatchschedule.data.mvp.model.LeagueResult
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchPresenter (
    val view : MatchView,
    val gson: Gson,
    private val apiRepository: ApiRepository,
    private val context: CoroutineContextProvider = CoroutineContextProvider()

) {

    fun getLeagueDetail(id : String?) {
        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getLeagueDetail(id)).await(),
                LeagueResult::class.java
            )

            if (data.leagues.isNotEmpty()) {
                view.showLeagueDetail(data.leagues[0])
            }
        }
    }
}