package com.solitelab.footballmatchschedule.mvp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LeagueDetail(
    @SerializedName("strBadge")
    var badge: String? = null,

    @SerializedName("strLeague")
    var name: String? = null,

    @SerializedName("strLeagueAlternate")
    var alternate: String? = null,

    @SerializedName("strCountry")
    var country: String? = null,

    @SerializedName("intFormedYear")
    var formed: String? = null,

    @SerializedName("strWebsite")
    var website: String? = null
) : Parcelable