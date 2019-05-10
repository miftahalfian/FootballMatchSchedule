package com.solitelab.footballmatchschedule.ui.layout

import android.graphics.Color
import android.graphics.Typeface
import android.support.constraint.ConstraintLayout.LayoutParams.PARENT_ID
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.solitelab.footballmatchschedule.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.constraint.layout.constraintLayout

class MatchItemUI : AnkoComponent<ViewGroup> {

    override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui) {
        cardView {
            layoutParams = FrameLayout.LayoutParams(matchParent, wrapContent).apply {
                margin = dip(8)
            }
            foreground = context.getDrawable(attr(android.R.attr.selectableItemBackground).resourceId)
            isClickable = true
            radius = dip(4).toFloat()

            constraintLayout {
                textView {
                    id = R.id.league_name
                    text = context.getString(R.string.league_name)
                    textColor = Color.BLACK
                    textSize = 20.0f
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams(width = wrapContent, height = wrapContent) {
                    topMargin = dimen(R.dimen.activity_small_margin)
                    marginStart = dimen(R.dimen.activity_small_margin)
                    marginEnd = dimen(R.dimen.activity_small_margin)
                    topToTop = PARENT_ID
                    startToStart = PARENT_ID
                    endToEnd = PARENT_ID
                }

                textView {
                    id = R.id.match_date
                    text = context.getString(R.string.date)
                    textColor = Color.BLACK
                }.lparams(width = wrapContent, height = wrapContent) {
                    topMargin = dip(4)
                    marginStart = dimen(R.dimen.activity_small_margin)
                    marginEnd = dimen(R.dimen.activity_small_margin)
                    topToBottom = R.id.league_name
                    startToStart = PARENT_ID
                    endToEnd = PARENT_ID
                }

                textView {
                    id = R.id.match_time
                    text = context.getString(R.string.time)
                    textColor = Color.BLACK
                }.lparams(width = wrapContent, height = wrapContent) {
                    topMargin = dip(4)
                    marginStart = dimen(R.dimen.activity_small_margin)
                    marginEnd = dimen(R.dimen.activity_small_margin)
                    topToBottom = R.id.match_date
                    startToStart = PARENT_ID
                    endToEnd = PARENT_ID
                }

                imageView {
                    id = R.id.home_logo
                    transitionName = context.getString(R.string.home_logo)
                    imageResource = android.R.color.darker_gray
                    scaleType = ImageView.ScaleType.FIT_XY
                }.lparams(width = dip(80), height = dip(80)) {
                    topMargin = dimen(R.dimen.activity_small_margin)
                    marginStart = dimen(R.dimen.activity_small_margin)
                    startToStart = PARENT_ID
                    topToBottom = R.id.match_time
                }

                imageView {
                    id = R.id.away_logo
                    transitionName = context.getString(R.string.away_logo)
                    imageResource = android.R.color.darker_gray
                    scaleType = ImageView.ScaleType.FIT_XY
                }.lparams(width = dip(80), height = dip(80)) {
                    topMargin = dimen(R.dimen.activity_small_margin)
                    marginEnd = dimen(R.dimen.activity_small_margin)
                    endToEnd = PARENT_ID
                    topToBottom = R.id.match_time
                }

                textView {
                    id = R.id.home_score
                    text = context.getString(R.string.initial_score)
                    textSize = 50.0f
                    textColor = Color.BLACK
                    typeface =  Typeface.DEFAULT_BOLD
                }.lparams(width = wrapContent, height = wrapContent) {
                    marginStart = dip(40)
                    bottomMargin = dimen(R.dimen.activity_small_margin)
                    startToEnd = R.id.home_logo
                    topToTop = R.id.home_logo
                    bottomToBottom = R.id.home_logo
                }

                textView {
                    id = R.id.away_score
                    text = context.getString(R.string.initial_score)
                    textSize = 50.0f
                    textColor = Color.BLACK
                    typeface =  Typeface.DEFAULT_BOLD
                }.lparams(width = wrapContent, height = wrapContent) {
                    marginEnd = dip(40)
                    bottomMargin = dimen(R.dimen.activity_small_margin)
                    endToStart = R.id.away_logo
                    topToTop = R.id.away_logo
                    bottomToBottom = R.id.away_logo
                }

                textView {
                    text = context.getString(R.string.score_separator)
                    textSize = 40.0f
                    textColor = Color.BLACK
                    typeface =  Typeface.DEFAULT_BOLD
                }.lparams(width = wrapContent, height = wrapContent) {
                    marginStart = dimen(R.dimen.activity_small_margin)
                    marginEnd = dimen(R.dimen.activity_small_margin)
                    startToEnd = R.id.home_score
                    endToStart = R.id.away_score
                    topToTop = R.id.home_score
                    bottomToBottom = R.id.away_score
                }

                textView {
                    id = R.id.home_team_name
                    text = context.getString(R.string.home_team)
                    textColor = Color.BLACK
                }.lparams(width = wrapContent, height = wrapContent) {
                    topMargin = dimen(R.dimen.activity_small_margin)
                    bottomMargin = dimen(R.dimen.activity_small_margin)
                    marginStart = dimen(R.dimen.activity_small_margin)
                    topToBottom = R.id.home_logo
                    bottomToBottom = PARENT_ID
                    startToStart = PARENT_ID
                }

                textView {
                    id = R.id.away_team_name
                    text = context.getString(R.string.home_team)
                    textColor = Color.BLACK
                }.lparams(width = wrapContent, height = wrapContent) {
                    topMargin = dimen(R.dimen.activity_small_margin)
                    bottomMargin = dimen(R.dimen.activity_small_margin)
                    marginEnd = dimen(R.dimen.activity_small_margin)
                    topToBottom = R.id.away_logo
                    bottomToBottom = PARENT_ID
                    endToEnd = PARENT_ID
                }
            }
        }
    }

}