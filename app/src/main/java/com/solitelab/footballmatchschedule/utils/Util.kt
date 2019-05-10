package com.solitelab.footballmatchschedule.utils

import android.util.Log
import android.view.View
import android.widget.ImageView
import com.google.gson.Gson
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.api.TheSportDBApi
import com.solitelab.footballmatchschedule.data.mvp.model.TeamResult
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun String.toDate(dateFormat: String = "yyyy-MM-dd HH:mm:ss", timeZone: TimeZone = TimeZone.getTimeZone("UTC")): Date {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.timeZone = timeZone
    return parser.parse(this)
}

fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}

fun loadTeamBadge(id : String?, listener: (String?) -> Unit) {
    GlobalScope.launch(Dispatchers.Main) {
        val data = Gson().fromJson(
            ApiRepository()
                .doRequest(TheSportDBApi.getTeamDetail(id)).await(),
            TeamResult::class.java
        )

        if (!data.teams[0].badge.isNullOrEmpty()) {
            listener(data.teams[0].badge)
        }
    }
}