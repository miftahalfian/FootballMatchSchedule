package com.solitelab.footballmatchschedule.data.mvp.teamdetail

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import com.solitelab.footballmatchschedule.data.db.database
import com.solitelab.footballmatchschedule.data.mvp.model.Team
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class TeamDetailPresenter(val view: TeamDetailView) {
    fun addToFavorite(team: Team, context: Context) {
        try {
            context.database.use {
                insert("TEAM",
                    Team.ID_TEAM to team.idTeam,
                    Team.TEAM_NAME to team.teamName,
                    Team.COUNTRY to team.country,
                    Team.SPORT to team.strSport,
                    Team.BADGE to team.badge,
                    Team.STADIUM to team.stadium,
                    Team.STADIUM_THUMB to team.stadiumThumb,
                    Team.STADIUM_LOCATION to team.stadiumLocation,
                    Team.STADIUM_CAPACITY to team.stadiumCapacity,
                    Team.STADIUM_DESCRIPTION to team.stadiumCapacity,
                    Team.DESCRIPTION to team.description
                )
            }
            view.onAddedToFavorite()
        } catch (e: Throwable){
            view.onShowError(e.localizedMessage)
        }
    }

    fun removeFromFavorite(team: Team, context: Context) {
        try {
            context.database.use {
                delete("TEAM", "(ID_TEAM = {id})", "id" to team.idTeam!!)
            }
            view.onRemovedFromFavorite()

        } catch (e: SQLiteConstraintException){
            view.onShowError(e.localizedMessage)
        }
    }

    fun isFavorite(team: Team, context: Context) : Boolean {
        var fav = false

        context.database.use {
            val result = select("TEAM")
                .whereArgs("(ID_TEAM = {id})",
                    "id" to team.idTeam!!)
            val favorite = result.parseList(classParser<Team>())
            fav = favorite.isNotEmpty()
        }
        return fav
    }
}