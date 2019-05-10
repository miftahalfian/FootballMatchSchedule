package com.solitelab.footballmatchschedule.ui

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.data.adapter.MatchPageAdapter
import com.solitelab.footballmatchschedule.ui.fragments.FavoriteLastMatchFragment
import com.solitelab.footballmatchschedule.ui.fragments.FavoriteNextMatchFragment
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

        val tabsFragment = ArrayList<Fragment>()
        tabsFragment.add(FavoriteLastMatchFragment())
        tabsFragment.add(FavoriteNextMatchFragment())

        val pageAdapter = MatchPageAdapter(supportFragmentManager, tabsFragment, tabsTitle)
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
