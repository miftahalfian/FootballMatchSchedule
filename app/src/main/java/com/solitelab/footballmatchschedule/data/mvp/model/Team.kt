package com.solitelab.footballmatchschedule.data.mvp.model

import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("strTeam")
    val teamName : String? = null,

    @SerializedName("strDescriptionEN")
    val description : String? = null,

    @SerializedName("strCountry")
    val country : String? = null,

    @SerializedName("strTeamBadge")
    val badge : String? = null
)