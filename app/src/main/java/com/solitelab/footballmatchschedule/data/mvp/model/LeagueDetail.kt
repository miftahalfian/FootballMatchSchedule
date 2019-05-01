package com.solitelab.footballmatchschedule.data.mvp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeagueDetail(
    @SerializedName("idLeague")
    var id : Int? = null,

    @SerializedName("strBadge")
    var badge: String? = null,

    @SerializedName("strFanart1")
    var fanArt: String? = null,

    @SerializedName("strLeague")
    var name: String? = null,

    @SerializedName("strLeagueAlternate")
    var alternate: String? = null,

    @SerializedName("strCountry")
    var country: String? = null,

    @SerializedName("intFormedYear")
    var formed: String? = null,

    @SerializedName("strWebsite")
    var website: String? = null,

    @SerializedName("strDescriptionEN")
    var description: String? = null
) : Parcelable