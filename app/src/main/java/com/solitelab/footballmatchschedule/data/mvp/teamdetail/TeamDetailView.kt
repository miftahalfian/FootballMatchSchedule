package com.solitelab.footballmatchschedule.data.mvp.teamdetail

interface TeamDetailView {
    fun onAddedToFavorite()
    fun onRemovedFromFavorite()
    fun onShowError(message: String)
}