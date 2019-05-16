package com.solitelab.footballmatchschedule.data.mvp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Player (
    val idPlayer: String?,

    @SerializedName("strThumb")
    val thumb: String?,

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

) : Parcelable