package com.solitelab.footballmatchschedule.data.mvp.team

import android.database.sqlite.SQLiteConstraintException
import com.google.gson.Gson
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.api.TheSportDBApi
import com.solitelab.footballmatchschedule.data.db.database
import com.solitelab.footballmatchschedule.data.mvp.model.Match
import com.solitelab.footballmatchschedule.data.mvp.model.Team
import com.solitelab.footballmatchschedule.data.mvp.model.TeamResult
import com.solitelab.footballmatchschedule.ui.MatchDetailActivity
import com.solitelab.footballmatchschedule.ui.TeamDetailActivity
import com.solitelab.footballmatchschedule.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.*
import org.jetbrains.anko.design.snackbar

class TeamPresenter (
    val view: TeamView,
    private val context: CoroutineContextProvider = CoroutineContextProvider(),
    private val gson: Gson = Gson(),
    private val apiRepository: ApiRepository = ApiRepository()
) {
    fun getTeams(id: Int?) {
        view.onLoadData()
        GlobalScope.launch(context.main){
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeams(id.toString())).await(),
                TeamResult::class.java
            )

            data.teams?.let {
                view.onDataLoaded(it)
            } ?: view.onLoadFailed()
        }
    }
}