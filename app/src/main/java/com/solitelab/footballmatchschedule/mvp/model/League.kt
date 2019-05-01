package com.solitelab.footballmatchschedule.mvp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class League(val name:String?, val image:Int?, val id:Int?) : Parcelable