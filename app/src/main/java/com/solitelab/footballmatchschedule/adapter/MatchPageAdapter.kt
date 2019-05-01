package com.solitelab.footballmatchschedule.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.solitelab.footballmatchschedule.fragments.LastMatchFragment
import com.solitelab.footballmatchschedule.fragments.NextMatchFragment

class MatchPageAdapter(
    val fragmentManager: FragmentManager, val fragmentList : List<Fragment>, val stringList: List<String>
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = fragmentList.get(position)

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence? = stringList.get(position)

}