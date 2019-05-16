package com.solitelab.footballmatchschedule.data.mvp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Team(
    val idTeam : String?,

    @SerializedName("strTeam")
    val teamName : String?,

    @SerializedName("strCountry")
    val country : String?,

    @SerializedName("strSport")
    val strSport: String?,

    @SerializedName("strTeamBadge")
    val badge : String?,

    @SerializedName("strStadium")
    val stadium: String?,

    @SerializedName("strStadiumThumb")
    val stadiumThumb: String?,

    @SerializedName("strStadiumLocation")
    val stadiumLocation: String?,

    @SerializedName("intStadiumCapacity")
    val stadiumCapacity: String?,

    @SerializedName("strStadiumDescription")
    val stadiumDescription: String?,

    @SerializedName("strDescriptionEN")
    val description : String?
) : Parcelable {
    companion object {
        const val ID_TEAM: String = "ID_TEAM"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val COUNTRY: String = "COUNTRY"
        const val SPORT: String = "SPORT"
        const val BADGE: String = "BADGE"
        const val STADIUM: String = "STADIUM"
        const val STADIUM_THUMB = "STADIUM_THUMB"
        const val STADIUM_LOCATION = "STADIUM_LOCATION"
        const val STADIUM_CAPACITY = "STADIUM_CAPACITY"
        const val STADIUM_DESCRIPTION = "STADIUM_DESCRIPTION"
        const val DESCRIPTION = "DESCRIPTION"
    }
}