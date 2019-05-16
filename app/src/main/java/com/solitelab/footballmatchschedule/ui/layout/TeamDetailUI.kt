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
import com.solitelab.footballmatchschedule.ui.TeamDetailActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.support.v4.viewPager

class TeamDetailUI : AnkoComponent<TeamDetailActivity> {
    lateinit var toolbar : Toolbar
    lateinit var appBar : AppBarLayout
    lateinit var tabLayout : TabLayout
    lateinit var viewPager: ViewPager
    lateinit var teamLogo : ImageView
    lateinit var teamName : TextView
    lateinit var teamCountry : TextView

    override fun createView(ui: AnkoContext<TeamDetailActivity>) = with(ui) {
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
                        teamLogo = imageView {
                            id = R.id.team_logo
                            imageResource = android.R.color.white
                            transitionName = context.getString(R.string.logo)
                        }.lparams(width = dip(100), height = dip(100)) {
                            topToTop = PARENT_ID
                            startToStart = PARENT_ID
                            endToEnd = PARENT_ID
                            topMargin = dip(56)
                        }

                        teamName = textView {
                            id = R.id.team_name
                            text = context.getString(R.string.team_name)
                            textColor = Color.WHITE
                            textSize = 20.0f
                            typeface = Typeface.DEFAULT_BOLD
                        }.lparams(width = wrapContent, height = wrapContent) {
                            topToBottom = R.id.team_logo
                            startToStart = PARENT_ID
                            endToEnd = PARENT_ID
                            topMargin = dimen(R.dimen.activity_small_margin)
                        }

                        teamCountry = textView {
                            id = R.id.country
                            text = context.getString(R.string.country)
                            textColor = Color.WHITE
                        }.lparams(width = wrapContent, height = wrapContent) {
                            topToBottom = R.id.team_name
                            bottomToBottom = PARENT_ID
                            startToStart = PARENT_ID
                            endToEnd = PARENT_ID
                            topMargin = dimen(R.dimen.activity_small_margin)
                            bottomMargin = dimen(R.dimen.activity_vertical_margin)
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