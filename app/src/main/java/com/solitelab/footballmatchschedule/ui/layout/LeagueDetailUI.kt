package com.solitelab.footballmatchschedule.ui.layout

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PARALLAX
import android.support.design.widget.CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.solitelab.footballmatchschedule.ui.LeagueDetailActivity
import com.solitelab.footballmatchschedule.R
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.toolbar
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.design.collapsingToolbarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.themedAppBarLayout
import org.jetbrains.anko.support.v4.nestedScrollView

class LeagueDetailUI: AnkoComponent<LeagueDetailActivity> {
    lateinit var leagueName : TextView
    lateinit var leagueId : TextView
    lateinit var leagueDesc : TextView
    lateinit var leagueCountry : TextView
    lateinit var leagueFormed : TextView
    lateinit var leagueWebsite : TextView
    lateinit var leagueLogo : ImageView
    lateinit var leagueBanner : ImageView
    lateinit var toolbar : Toolbar
    lateinit var appBar : AppBarLayout
    lateinit var collapsingToolbar: CollapsingToolbarLayout

    override fun createView(ui: AnkoContext<LeagueDetailActivity>) = with(ui) {
        coordinatorLayout {
            fitsSystemWindows = true

            appBar = themedAppBarLayout(R.style.AppTheme_AppBarOverlay) {
                id = R.id.app_bar
                fitsSystemWindows = true

                collapsingToolbar = collapsingToolbarLayout {
                    fitsSystemWindows = true
                    isTitleEnabled = true
                    contentScrim = ColorDrawable(colorAttr(R.attr.colorPrimary))
                    expandedTitleGravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
                    setExpandedTitleTextAppearance(R.style.ExpandedAppBar)

                    leagueBanner = imageView {
                        imageResource = R.color.colorPrimary
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }.lparams(width = matchParent, height = matchParent) {
                        collapseMode = COLLAPSE_MODE_PARALLAX
                    }

                    view {
                        background = context.getDrawable(R.drawable.gradient)
                    }.lparams(width = matchParent, height = dip(100)) {
                        gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
                    }

                    toolbar = toolbar {
                        id = R.id.toolbar
                        popupTheme = R.style.AppTheme_AppBarOverlay
                    }.lparams(width = matchParent, height = dimenAttr(R.attr.actionBarSize)) {
                        collapseMode = COLLAPSE_MODE_PIN
                    }

                }.lparams(width = matchParent, height = dip(256)) {
                    scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL or AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED
                }

            }.lparams(width = matchParent)

            nestedScrollView {
                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    padding = dip(16)

                    cardView {
                        verticalLayout {
                            padding = dip(8)

                            leagueLogo = imageView {
                                id = R.id.league_logo
                                imageResource = R.drawable.american_mayor_league
                                transitionName = context.getString(R.string.league_logo)
                            }.lparams(width = matchParent, height = dip(100)) {
                                gravity = Gravity.CENTER
                            }

                            leagueName = textView {
                                id = R.id.league_name
                                text = context.getString(R.string.league_name)
                                textSize = 20f //sp
                                setTypeface(typeface, Typeface.BOLD)
                                textColor = Color.BLACK
                                textAlignment = View.TEXT_ALIGNMENT_CENTER
                            }.lparams(width = wrapContent, height = wrapContent) {
                                gravity = Gravity.CENTER
                            }

                            leagueId = textView {
                                id = R.id.league_id
                                text = context.getString(R.string.league_id)
                                textColor = Color.BLACK
                            }.lparams(width = wrapContent, height = wrapContent) {
                                gravity = Gravity.CENTER
                            }

                        }.lparams(width = matchParent)
                    }.lparams(width = matchParent)

                    cardView {
                        verticalLayout {
                            padding = dip(8)
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL

                                textView {
                                    text = context.getString(R.string.country)
                                    textColor = Color.BLACK
                                    setTypeface(typeface, Typeface.BOLD)
                                }.lparams(dip(0), wrapContent) {
                                    weight = 1.0f
                                }

                                leagueCountry = textView {
                                    textColor = Color.BLACK
                                }.lparams(dip(0), wrapContent) {
                                    weight = 4.0f
                                }

                            }.lparams(width = matchParent) {
                                topMargin = dip(8)
                            }

                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL

                                textView {
                                    text = context.getString(R.string.formed)
                                    textColor = Color.BLACK
                                    setTypeface(typeface, Typeface.BOLD)
                                }.lparams(dip(0), wrapContent) {
                                    weight = 1.0f
                                }

                                leagueFormed = textView {
                                    textColor = Color.BLACK
                                }.lparams(dip(0), wrapContent) {
                                    weight = 4.0f
                                }

                            }.lparams(width = matchParent)

                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL

                                textView {
                                    text = context.getString(R.string.website)
                                    textColor = Color.BLACK
                                    setTypeface(typeface, Typeface.BOLD)
                                }.lparams(dip(0), wrapContent) {
                                    weight = 1.0f
                                }

                                leagueWebsite = textView {
                                    textColor = Color.BLACK
                                }.lparams(dip(0), wrapContent) {
                                    weight = 4.0f
                                }

                            }.lparams(width = matchParent) {
                                bottomMargin = dip(16)
                            }
                            
                            textView { 
                                text = context.getString(R.string.description)
                                textColor = Color.BLACK
                                setTypeface(typeface, Typeface.BOLD)
                            }
                            
                            leagueDesc = textView {
                                id = R.id.league_desc
                                textColor = Color.BLACK
                                text = context.getString(R.string.large_text)
                            }.lparams(width = matchParent, height = wrapContent)
                            
                        }.lparams(width= matchParent, height = wrapContent)

                    }.lparams(width = matchParent) {
                        topMargin = dimen(R.dimen.activity_small_margin)
                    }
                }.lparams(width = matchParent)

            }.lparams(width = matchParent, height = matchParent) {
                behavior = Class.forName(context.getString(R.string.appbar_scrolling_view_behavior)).newInstance() as CoordinatorLayout.Behavior<*>?
            }
        }
    }

}