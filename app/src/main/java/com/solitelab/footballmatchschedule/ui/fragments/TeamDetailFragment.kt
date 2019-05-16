package com.solitelab.footballmatchschedule.ui.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.solitelab.footballmatchschedule.R
import com.solitelab.footballmatchschedule.data.mvp.model.Team
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

// TODO: Rename parameter arguments, choose names that match
class TeamDetailFragment : Fragment() {
    var team : Team? = null
    lateinit var description: TextView
    lateinit var stadiumImg: ImageView
    lateinit var stadiumName: TextView
    lateinit var stadiumLocation: TextView
    lateinit var stadiumCapacity: TextView
    lateinit var stadiumDescription: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_team_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        description = view.find(R.id.description)
        stadiumImg = view.find(R.id.stadium_img)
        stadiumName = view.find(R.id.stadium_name)
        stadiumLocation = view.find(R.id.stadium_location)
        stadiumCapacity = view.find(R.id.stadium_capacity)
        stadiumDescription = view.find(R.id.stadium_description)

        team = activity?.intent?.getParcelableExtra("team")

        team?.let {
            description.text = it.description
            stadiumName.text = "%s %s".format(getString(R.string.name), it.stadium)
            stadiumLocation.text = "%s %s".format(getString(R.string.location), it.stadiumLocation)
            stadiumCapacity.text = "%s %s".format(getString(R.string.capacity), it.stadiumCapacity)
            stadiumDescription.text = it.stadiumDescription
            Picasso.get().load(it.stadiumThumb).into(stadiumImg)
        }
    }
}
