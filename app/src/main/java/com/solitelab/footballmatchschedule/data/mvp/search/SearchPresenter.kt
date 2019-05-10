package com.solitelab.footballmatchschedule.data.mvp.search

import com.google.gson.Gson
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.api.TheSportDBApi
import com.solitelab.footballmatchschedule.data.mvp.model.SearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchPresenter(val view:SearchView,
                      val gson: Gson,
                      private val apiRepository: ApiRepository
) {
    fun search(query: String?) {
        if (query == null || query.isEmpty()) return

        val encodedQuery = query.replace(" ", "_")
        view.onLoadData()

        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.searchMatch(encodedQuery)).await(),
                SearchResult::class.java
            )

            view.onDataLoaded(data.event)
        }
    }
}