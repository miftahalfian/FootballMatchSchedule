package com.solitelab.footballmatchschedule

import android.support.test.espresso.matcher.BoundedMatcher
import android.support.v7.widget.RecyclerView
import android.view.View
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object Util {
    fun withIndex(matcher: Matcher<View>, index: Int) : Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            var currentIndex = 0

            override fun describeTo(description: Description) {
                description.appendText("with index: ")
                description.appendValue(index)
                matcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                return matcher.matches(view) && currentIndex++ == index
            }
        }
    }

    fun hasItemm(matcher: Matcher<View>) : Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("has item: ")
                matcher.describeTo(description)
            }

            override fun matchesSafely(item: RecyclerView): Boolean {
                val adapter = item.adapter

                for (i in 0 until adapter!!.itemCount) {
                    val type = adapter.getItemViewType(i)
                    val holder = adapter.createViewHolder(item, type)

                    adapter.onBindViewHolder(holder, i)

                    if (matcher.matches(holder.itemView)) {
                        return true
                    }
                }

                return false
            }
        }
    }
}