package com.solitelab.footballmatchschedule.data.mvp.search

import com.google.gson.Gson
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.api.TheSportDBApi
import com.solitelab.footballmatchschedule.data.mvp.model.SearchResult
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SearchPresenter(val view:SearchView, val gson: Gson, private val apiRepository: ApiRepository) {
    fun search(query: String?) {
        if (query == null || query.isEmpty()) return

        val encodedQuery = query.replace(" ", "_")
        view.onLoadData()
        doAsync {
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.searchMatch(encodedQuery)),
                SearchResult::class.java
            )

            uiThread {
                view.onDataLoaded(data.event)
            }
        }
    }
}