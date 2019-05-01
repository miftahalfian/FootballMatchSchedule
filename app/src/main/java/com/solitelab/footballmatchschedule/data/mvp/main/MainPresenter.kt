package com.solitelab.footballmatchschedule.data.mvp.main

import android.content.Context
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.data.mvp.model.League

class MainPresenter (val view: MainView) {
    fun setupData() {
        val items: MutableList<League> = mutableListOf()

        val name = (view as Context).resources.getStringArray(R.array.league_name)
        val image = (view as Context).resources.obtainTypedArray(R.array.league_image)
        val id = (view as Context).resources.getIntArray(R.array.league_id)
        items.clear()
        for (i in name.indices) {
            items.add(
                League(
                    name[i],
                    image.getResourceId(i, 0),
                    id[i]
                )
            )
        }

        image.recycle()

        view.setData(items)
    }
}