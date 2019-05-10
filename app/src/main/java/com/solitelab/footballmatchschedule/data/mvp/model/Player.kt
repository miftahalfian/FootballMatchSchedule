package com.solitelab.footballmatchschedule.data.mvp.model

import com.google.gson.annotations.SerializedName

data class Player (
    @SerializedName("strPlayer")
    val name: String?,

    @SerializedName("strTeam")
    val team: String?,

    @SerializedName("strPosition")
    val position: String?,

    @SerializedName("strNationality")
    val nationality: String?,

    @SerializedName("dateBorn")
    val dateBorn: String?,

    @SerializedName("strBirthLocation")
    val birthLocation: String?,

    @SerializedName("strHeight")
    val height: String?,

    @SerializedName("strWeight")
    val weight: String?,

    @SerializedName("strDescriptionEN")
    val description: String?,

    @SerializedName("strFacebook")
    val facebook: String?,

    @SerializedName("strTwitter")
    val twitter: String?,

    @SerializedName("strInstagram")
    val instagram: String?

)