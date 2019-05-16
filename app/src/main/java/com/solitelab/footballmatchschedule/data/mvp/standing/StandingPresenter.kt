package com.solitelab.footballmatchschedule.data.mvp.standing

import com.google.gson.Gson
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.api.TheSportDBApi
import com.solitelab.footballmatchschedule.data.mvp.model.StandingResult
import com.solitelab.footballmatchschedule.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StandingPresenter (val view:StandingView,
                         val gson: Gson,
                         val apiRepository: ApiRepository,
                         private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getStandings(leagueID: Int?) {
        view.onLoadData()
        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getStandings(leagueID.toString())).await(),
                StandingResult::class.java
            )

            data.table.let {
                view.onDataLoaded(it)
            }
        }
    }
}