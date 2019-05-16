package com.solitelab.footballmatchschedule.data.mvp.favorite

import android.content.Context
import com.solitelab.footballmatchschedule.data.db.database
import com.solitelab.footballmatchschedule.data.mvp.model.Team
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteTeamPresenter(val view:FavoriteTeamView) {
    fun loadTeamFavorite(context: Context?) {
        view.onLoadData()

        context?.database?.use {
            val result = select("TEAM")
            val favorite = result.parseList(classParser<Team>())
            view.onDataLoaded(favorite)
        }
    }
}