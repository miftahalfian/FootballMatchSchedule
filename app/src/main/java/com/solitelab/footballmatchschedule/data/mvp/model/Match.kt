package com.solitelab.footballmatchschedule.data.mvp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Match (
    @SerializedName("idEvent")
    val id: String? = null,

    @SerializedName("strLeague")
    val league: String? = null,

    @SerializedName("dateEvent")
    val date: String? = null,

    @SerializedName("strTime")
    val time: String? = null,

    @SerializedName("idHomeTeam")
    val homeTeamID: String? = null,

    @SerializedName("strHomeTeam")
    val homeTeamName: String? = null,

    @SerializedName("intHomeScore")
    val homeScore: Int? = null,

    @SerializedName("strHomeGoalDetails")
    val homeGoalDetail: String? = null,

    @SerializedName("strHomeLineupGoalkeeper")
    val homeGoalKeeper: String? = null,

    @SerializedName("strHomeLineupDefense")
    val homeDefence: String? = null,

    @SerializedName("strHomeLineupMidfield")
    val homeMidfield: String? = null,

    @SerializedName("strHomeLineupForward")
    val homeForward: String? = null,

    @SerializedName("strHomeLineupSubstitutes")
    val homeSubstitutes: String? = null,

    @SerializedName("strHomeRedCards")
    val homeRedCards: String? = null,

    @SerializedName("strHomeYellowCards")
    val homeYellowCards: String? = null,

    @SerializedName("idAwayTeam")
    val awayTeamID: String? = null,

    @SerializedName("strAwayTeam")
    val awayTeamName: String? = null,

    @SerializedName("intAwayScore")
    val awayScore: Int? = null,

    @SerializedName("strAwayGoalDetails")
    val awayGoalDetail: String? = null,

    @SerializedName("strAwayLineupGoalkeeper")
    val awayGoalKeeper: String? = null,

    @SerializedName("strAwayLineupDefense")
    val awayDefence: String? = null,

    @SerializedName("strAwayLineupMidfield")
    val awayMidfield: String? = null,

    @SerializedName("strAwayLineupForward")
    val awayForward: String? = null,

    @SerializedName("strAwayLineupSubstitutes")
    val awaySubstitutes: String? = null,

    @SerializedName("strAwayRedCards")
    val awayRedCards: String? = null,

    @SerializedName("strAwayYellowCards")
    val awayYellowCards: String? = null
) : Parcelable {
    companion object {
        const val ID: String = "ID"
        const val LEAGUE: String = "LEAGUE"
        const val DATE_EVENT: String = "DATE_EVENT"
        const val TIME: String = "TIME"
        const val HOME_TEAM_ID = "HOME_TEAM_ID"
        const val HOME_TEAM_NAME = "HOME_TEAM_NAME"
        const val HOME_SCORE = "HOME_SCORE"
        const val HOME_GOAL_DETAILS = "HOME_GOAL_DETAILS"
        const val HOME_GOAL_KEEPER = "HOME_GOAL_KEEPER"
        const val HOME_DEFENSE = "HOME_DEFENSE"
        const val HOME_MIDFIELD = "HOME_MIDFIELD"
        const val HOME_FORWARD = "HOME_FORWARD"
        const val HOME_SUBSTITUTES = "HOME_SUBSTITUTES"
        const val HOME_RED_CARDS = "HOME_RED_CARDS"
        const val HOME_YELLOW_CARDS = "HOME_YELLOW_CARDS"
        const val AWAY_TEAM_ID = "AWAY_TEAM_ID"
        const val AWAY_TEAM_NAME = "AWAY_TEAM_NAME"
        const val AWAY_SCORE = "AWAY_SCORE"
        const val AWAY_GOAL_DETAILS = "AWAY_GOAL_DETAILS"
        const val AWAY_GOAL_KEEPER = "AWAY_GOAL_KEEPER"
        const val AWAY_DEFENSE = "AWAY_DEFENSE"
        const val AWAY_MIDFIELD = "AWAY_MIDFIELD"
        const val AWAY_FORWARD = "AWAY_FORWARD"
        const val AWAY_SUBSTITUTES = "AWAY_SUBSTITUTES"
        const val AWAY_RED_CARDS = "AWAY_RED_CARDS"
        const val AWAY_YELLOW_CARDS = "AWAY_YELLOW_CARDS"
    }
}