package com.solitelab.footballmatchschedule.data.mvp.matchdetail

import android.database.sqlite.SQLiteConstraintException
import com.google.gson.Gson
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.api.TheSportDBApi
import com.solitelab.footballmatchschedule.data.db.database
import com.solitelab.footballmatchschedule.data.mvp.model.Match
import com.solitelab.footballmatchschedule.data.mvp.model.TeamResult
import com.solitelab.footballmatchschedule.ui.MatchDetailActivity
import com.solitelab.footballmatchschedule.utils.toDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.db.*
import org.jetbrains.anko.design.snackbar
import java.util.*

class MatchDetailPresenter(val view : MatchDetailView) {

    fun loadTeamBadge(id : String?, tag : String) {
        GlobalScope.launch(Dispatchers.Main){
            val data = Gson().fromJson(ApiRepository()
                .doRequest(TheSportDBApi.getTeamDetail(id)).await(),
                TeamResult::class.java
            )

            data.teams?.let {
                view.onBadgeLoaded(it[0].badge, tag)
            }
        }
    }

    fun addToFavorite(match : Match?) {
        val date = match?.date!!.toDate("yyyy-MM-dd")
        val currentDate = getCurrentDateTime()

        val tableName = if (date > currentDate) "NEXT_MATCH" else "LAST_MATCH"
        val context = view as MatchDetailActivity

        try {
            context.database.use {
                insert(tableName,
                    Match.ID to match.id,
                    Match.LEAGUE to match.league,
                    Match.SPORT to match.strSport,
                    Match.DATE_EVENT to match.date,
                    Match.TIME to match.time,
                    Match.HOME_TEAM_ID to match.homeTeamID,
                    Match.HOME_TEAM_NAME to match.homeTeamName,
                    Match.HOME_SCORE to match.homeScore,
                    Match.HOME_GOAL_DETAILS to match.homeGoalDetail,
                    Match.HOME_GOAL_KEEPER to match.homeGoalKeeper,
                    Match.HOME_DEFENSE to match.homeDefence,
                    Match.HOME_MIDFIELD to match.homeMidfield,
                    Match.HOME_FORWARD to match.homeForward,
                    Match.HOME_SUBSTITUTES to match.homeSubstitutes,
                    Match.HOME_RED_CARDS to match.homeRedCards,
                    Match.HOME_YELLOW_CARDS to match.homeYellowCards,
                    Match.AWAY_TEAM_ID to match.awayTeamID,
                    Match.AWAY_TEAM_NAME to match.awayTeamName,
                    Match.AWAY_SCORE to match.awayScore,
                    Match.AWAY_GOAL_DETAILS to match.awayGoalDetail,
                    Match.AWAY_GOAL_KEEPER to match.awayGoalKeeper,
                    Match.AWAY_DEFENSE to match.awayDefence,
                    Match.AWAY_MIDFIELD to match.awayMidfield,
                    Match.AWAY_FORWARD to match.awayForward,
                    Match.AWAY_SUBSTITUTES to match.awaySubstitutes,
                    Match.AWAY_RED_CARDS to match.awayRedCards,
                    Match.AWAY_YELLOW_CARDS to match.awayYellowCards
                )
            }
            context.getRootView().snackbar("Added to favorite").show()
        } catch (e: Throwable){
            context.getRootView().snackbar(e.localizedMessage).show()
        }
    }

    fun removeFromFavorite(match: Match?) {
        val date = match?.date!!.toDate("yyyy-MM-dd")
        val currentDate = getCurrentDateTime()

        val tableName = if (date > currentDate) "NEXT_MATCH" else "LAST_MATCH"
        val context = view as MatchDetailActivity

        try {
            context.database.use {
                delete(tableName, "(ID = {id})", "id" to match.id!!)
            }
            context.getRootView().snackbar("Removed from favorite").show()

        } catch (e: SQLiteConstraintException){
            context.getRootView().snackbar(e.localizedMessage).show()
        }
    }

    fun isFavorite(match: Match?) : Boolean {
        val date = match?.date!!.toDate("yyyy-MM-dd")
        val currentDate = getCurrentDateTime()

        val tableName = if (date > currentDate) "NEXT_MATCH" else "LAST_MATCH"
        val context = view as MatchDetailActivity

        var isMatch = false

        context.database.use {
            val result = select(tableName)
                .whereArgs("(ID = {id})",
                    "id" to match.id!!)
            val favorite = result.parseList(classParser<Match>())
            isMatch = favorite.isNotEmpty()
        }

        return isMatch
    }

    private fun getCurrentDateTime() = Calendar.getInstance().time
}