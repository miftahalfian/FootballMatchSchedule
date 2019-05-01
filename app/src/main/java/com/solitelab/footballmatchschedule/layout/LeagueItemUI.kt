package com.solitelab.footballleague.layout

import android.graphics.Color
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.solitelab.footballmatchschedule.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class LeagueItemUI : AnkoComponent<ViewGroup> {
    companion object {
        const val imgId = 1
        const val tvNameId = 2
    }

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {

        cardView {
            layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent).apply {
                margin = dip(8)
            }
            backgroundColor = Color.WHITE
            isClickable = true
            radius = dip(4).toFloat()

            relativeLayout {
                padding = dip(8)

                val image = imageView {
                     id= imgId
                     imageResource = R.color.colorPrimary
                     scaleType = ImageView.ScaleType.FIT_CENTER
                    transitionName = resources.getString(R.string.league_logo)

                }.lparams(width= matchParent, height = dip(150)) {
                     alignParentStart()
                     alignParentTop()

                }

                textView {
                    id= tvNameId
                    text=context.getString(R.string.league_name)
                    textSize = 15.0f
                    textColor = Color.BLACK
                    typeface = Typeface.DEFAULT_BOLD
                    textAlignment = View.TEXT_ALIGNMENT_CENTER
                }.lparams(width= matchParent) {
                    below(image)
                }
            }.lparams(width = matchParent, height = wrapContent)
        }
    }
}