package com.solitelab.footballmatchschedule.ui.layout

import android.content.Context
import android.view.Gravity
import android.widget.TextView
import com.solitelab.footballmatchschedule.R
import org.jetbrains.anko.*

class NoResult : AnkoComponent<Context> {
    override fun createView(ui: AnkoContext<Context>) = with(ui) {
        verticalLayout {
            id = R.id.no_result
            lparams(matchParent, matchParent) {
                gravity = Gravity.CENTER
            }

            imageView {
                imageResource = R.drawable.ic_sentiment_dissatisfied_black
            }.lparams(width = matchParent, height = dip(48))

            textView {
                text = context.getString(R.string.no_result)
                textSize = 24.0f
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            }.lparams(width = matchParent, height = wrapContent)
        }
    }
}