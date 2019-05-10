package com.solitelab.footballmatchschedule.data.mvp.model

data class Standing (
    val name: String?,
    val teamId: String?,
    val played: Int?,
    val goalsFor: Int?,
    val goalsAgainst: Int?,
    val goalsDifference: Int?,
    val win: Int?,
    val draw: Int?,
    val loss: Int?,
    val total: Int?
)