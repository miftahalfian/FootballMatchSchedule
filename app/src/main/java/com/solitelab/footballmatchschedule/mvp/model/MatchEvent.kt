package com.solitelab.footballmatchschedule.mvp.model

import android.util.Log

class MatchEvent {
    var time : Int = 0
    var type : EventType = EventType.SHOT
    var team : TeamType = TeamType.HOME
    var player : String? = null

    enum class EventType {
        SHOT, YELLOW_CARD, RED_CARD
    }

    enum class TeamType {
        HOME, AWAY
    }

    fun parse(text : String, type: EventType, team: TeamType) {
        val data = text.split(':')

        var timeTemp = data[0].trim()
        var r = timeTemp.takeWhile { it.isDigit() }

        this.time = r.toInt()

        this.player = data[1].trim()

        this.type = type

        this.team = team

        Log.d("Match Event", "Time: ${time} Player: ${player}")
    }

}