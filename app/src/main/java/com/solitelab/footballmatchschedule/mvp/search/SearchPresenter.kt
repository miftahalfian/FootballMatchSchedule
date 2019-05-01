package com.solitelab.footballmatchschedule.mvp.search

import com.google.gson.Gson
import com.solitelab.footballmatchschedule.api.ApiRepository
import com.solitelab.footballmatchschedule.api.TheSportDBApi
import com.solitelab.footballmatchschedule.mvp.model.SearchResult
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.net.URLEncoder

class SearchPresenter(val view:SearchView, val gson: Gson, val apiRepository: ApiRepository) {
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