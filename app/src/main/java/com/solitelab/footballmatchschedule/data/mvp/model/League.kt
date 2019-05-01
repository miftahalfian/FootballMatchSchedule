package com.solitelab.footballmatchschedule.data.mvp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League(val name:String?, val image:Int?, val id:Int?) : Parcelable