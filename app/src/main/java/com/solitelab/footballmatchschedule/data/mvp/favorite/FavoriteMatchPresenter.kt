package com.solitelab.footballmatchschedule.data.mvp.favorite

import android.content.Context
import com.solitelab.footballmatchschedule.data.db.database
import com.solitelab.footballmatchschedule.data.mvp.model.Match
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteMatchPresenter(val view: FavoriteMatchView) {
    fun loadLastMatchFavorite(context: Context?) {
        view.onLoadData()

        context?.database?.use {
            val result = select("LAST_MATCH")
            val favorite = result.parseList(classParser<Match>())
            view.onDataLoaded(favorite)
        }
    }

    fun loadNextMatchFavorite(context: Context?) {
        view.onLoadData()

        context?.database?.use {
            val result = select("NEXT_MATCH")
            val favorite = result.parseList(classParser<Match>())
            view.onDataLoaded(favorite)
        }
    }
}