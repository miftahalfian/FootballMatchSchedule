package com.solitelab.footballmatchschedule.mvp.model

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
) : Parcelable