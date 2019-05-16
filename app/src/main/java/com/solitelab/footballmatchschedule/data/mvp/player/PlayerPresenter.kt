package com.solitelab.footballmatchschedule.data.mvp.player

import com.google.gson.Gson
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.api.TheSportDBApi
import com.solitelab.footballmatchschedule.data.mvp.model.PlayerResult
import com.solitelab.footballmatchschedule.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerPresenter(val view: PlayerView,
                      val gson: Gson,
                      val apiRepository: ApiRepository,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()
) {
    fun getPlayers(teamId: String?) {
        view.onLoadData()
        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPlayers(teamId.toString())).await(),
                PlayerResult::class.java
            )

            data.player?.let {
                view.onDataLoaded(it)
            } ?: view.onLoadFailed()
        }
    }
}