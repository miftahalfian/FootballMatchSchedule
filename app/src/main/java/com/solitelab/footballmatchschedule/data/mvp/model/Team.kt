package com.solitelab.footballmatchschedule.data.mvp.model

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("strTeam")
    val teamName : String?,

    @SerializedName("strCountry")
    val country : String?,

    @SerializedName("strTeamBadge")
    val badge : String?,

    @SerializedName("intFormedYear")
    val formedYear : String?,

    @SerializedName("strStadium")
    val stadium: String?,

    @SerializedName("strStadiumThumb")
    val stadiumThumb: String?,

    @SerializedName("strDescriptionEN")
    val description : String?
)