package com.solitelab.footballmatchschedule.data.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MatchPageAdapter(
    fragmentManager: FragmentManager,
    private val fragmentList : List<Fragment>,
    private val stringList: List<String>
    ) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence? = stringList[position]

}