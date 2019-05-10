package com.solitelab.footballmatchschedule.ui.layout

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.support.constraint.ConstraintLayout.LayoutParams.PARENT_ID
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.TabLayout
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.ui.MatchActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.support.v4.viewPager

class MatchUI : AnkoComponent<MatchActivity> {
    lateinit var toolbar : Toolbar
    lateinit var appBar : AppBarLayout
    lateinit var tabLayout : TabLayout
    lateinit var viewPager: ViewPager

    lateinit var leagueLogo : ImageView
    lateinit var leagueName :TextView
    lateinit var leagueAlternative : TextView
    lateinit var leagueCountry : TextView
    lateinit var leagueFormed : TextView
    lateinit var leagueWebsite : TextView

    override fun createView(ui: AnkoContext<MatchActivity>) = with(ui) {
        coordinatorLayout {
            fitsSystemWindows = false

            viewPager = viewPager{
                id = R.id.viewpager
            }.lparams(matchParent, matchParent) {
                behavior = Class.forName(context.getString(R.string.appbar_scrolling_view_behavior)).newInstance() as CoordinatorLayout.Behavior<*>?
            }

            appBar = themedAppBarLayout(R.style.AppTheme_AppBarOverlay) {
                id = R.id.app_bar
                fitsSystemWindows = true

                collapsingToolbarLayout {
                    fitsSystemWindows = true
                    isTitleEnabled = false
                    contentScrim = ColorDrawable(colorAttr(R.attr.colorPrimary))

                    constraintLayout {
                        leagueLogo = imageView {
                            id = R.id.league_logo
                            imageResource = R.drawable.english_premier_league
                            transitionName = context.getString(R.string.league_logo)
                        }.lparams(width= dip(100), height = dip(100)) {
                            marginStart = dimen(R.dimen.activity_horizontal_margin)
                            topMargin = dimen(R.dimen.activity_small_margin)
                            bottomMargin = dimen(R.dimen.activity_small_margin)
                            topToTop = R.id.league_name_top
                            bottomToBottom = R.id.league_website
                            startToStart = PARENT_ID
                        }

                        leagueName = textView {
                            id = R.id.league_name_top
                            text = context.getString(R.string.league_name)
                            textColor = Color.WHITE
                            textSize = 20.0f
                            setTypeface(typeface, Typeface.BOLD)
                        }.lparams(width = wrapContent, height = wrapContent) {
                            marginStart = dimen(R.dimen.activity_horizontal_margin)
                            topMargin = dip(56)
                            startToEnd = R.id.league_logo
                            topToTop = PARENT_ID
                        }

                        leagueAlternative = textView {
                            id = R.id.league_alternative
                            text = context.getString(R.string.alternative)
                            textColor = Color.WHITE
                        }.lparams(width = wrapContent, height = wrapContent) {
                            topMargin = dimen(R.dimen.activity_small_margin)
                            marginStart = dimen(R.dimen.activity_horizontal_margin)
                            marginEnd = dimen(R.dimen.activity_horizontal_margin)
                            topToBottom = R.id.league_name_top
                            startToEnd = R.id.league_logo
                        }

                        leagueCountry = textView {
                            id = R.id.league_country
                            text = context.getString(R.string.country)
                            textColor = Color.WHITE
                        }.lparams(width = wrapContent, height = wrapContent) {
                            topMargin = dimen(R.dimen.activity_small_margin)
                            marginStart = dimen(R.dimen.activity_horizontal_margin)
                            marginEnd = dimen(R.dimen.activity_horizontal_margin)
                            topToBottom = R.id.league_alternative
                            startToEnd = R.id.league_logo
                        }

                        leagueFormed = textView {
                            id = R.id.league_formed
                            text = context.getString(R.string.country)
                            textColor = Color.WHITE
                        }.lparams(width = wrapContent, height = wrapContent) {
                            topMargin = dimen(R.dimen.activity_small_margin)
                            marginStart = dimen(R.dimen.activity_horizontal_margin)
                            marginEnd = dimen(R.dimen.activity_horizontal_margin)
                            topToBottom = R.id.league_country
                            startToEnd = R.id.league_logo
                        }

                        leagueWebsite = textView {
                            id = R.id.league_website
                            text = context.getString(R.string.country)
                            textColor = Color.WHITE
                        }.lparams(width = wrapContent, height = wrapContent) {
                            topMargin = dimen(R.dimen.activity_small_margin)
                            marginStart = dimen(R.dimen.activity_horizontal_margin)
                            marginEnd = dimen(R.dimen.activity_horizontal_margin)
                            bottomMargin = dimen(R.dimen.activity_vertical_margin)
                            topToBottom = R.id.league_formed
                            startToEnd = R.id.league_logo
                            bottomToBottom = PARENT_ID
                        }
                    }.lparams(width = matchParent, height = wrapContent) {
                        collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
                    }

                    toolbar = toolbar {
                        id = R.id.toolbar
                        popupTheme = R.style.AppTheme_AppBarOverlay
                    }.lparams(width = matchParent, height = dimenAttr(R.attr.actionBarSize)) {
                        collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
                    }

                }.lparams(width = matchParent, height = wrapContent) {
                    scrollFlags =
                        AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                }

                tabLayout = tabLayout {
                    setTabTextColors(ContextCompat.getColor(context, R.color.colorPrimaryLight), ContextCompat.getColor(context, R.color.white))
                    setSelectedTabIndicatorColor(Color.WHITE)
                    background = ColorDrawable(colorAttr(R.attr.colorPrimary))
                    tabGravity = TabLayout.GRAVITY_FILL
                    tabMode = TabLayout.MODE_FIXED
                }.lparams(width= matchParent, height = dimenAttr(R.attr.actionBarSize)) {
                    gravity = Gravity.BOTTOM
                }

            }.lparams(width = matchParent)

        }
    }
}