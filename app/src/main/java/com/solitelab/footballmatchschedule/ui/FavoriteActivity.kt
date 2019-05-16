package com.solitelab.footballmatchschedule.ui

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.data.adapter.PageAdapter
import com.solitelab.footballmatchschedule.ui.fragments.FavoriteLastMatchFragment
import com.solitelab.footballmatchschedule.ui.fragments.FavoriteNextMatchFragment
import com.solitelab.footballmatchschedule.ui.fragments.FavoriteTeamFragment
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Favorite Match"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val tabsTitle = ArrayList<String>()
        tabsTitle.add("LAST MATCH")
        tabsTitle.add("NEXT MATCH")
        tabsTitle.add("TEAM")

        val tabsFragment = ArrayList<Fragment>()
        tabsFragment.add(FavoriteLastMatchFragment())
        tabsFragment.add(FavoriteNextMatchFragment())
        tabsFragment.add(FavoriteTeamFragment())

        val pageAdapter = PageAdapter(supportFragmentManager, tabsFragment, tabsTitle)
        container.adapter = pageAdapter

        tabs.setupWithViewPager(container)

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                finishAfterTransition()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
