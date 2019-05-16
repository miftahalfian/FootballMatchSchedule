package com.solitelab.footballmatchschedule.data.mvp.player

import com.google.gson.Gson
import com.solitelab.footballmatchschedule.TestContextProvider
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.mvp.model.Player
import com.solitelab.footballmatchschedule.data.mvp.model.PlayerResult
import com.solitelab.footballmatchschedule.data.mvp.model.Team
import com.solitelab.footballmatchschedule.data.mvp.model.TeamResult
import com.solitelab.footballmatchschedule.data.mvp.team.TeamPresenter
import com.solitelab.footballmatchschedule.data.mvp.team.TeamView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PlayerPresenterTest {
    @Mock
    private lateinit var view: PlayerView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: PlayerPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PlayerPresenter(view, gson, apiRepository, TestContextProvider())
    }

    @Test
    fun getPlayers() {
        val teamId = "133604"
        val players : List<Player> = mutableListOf()
        val response = PlayerResult(players)

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    PlayerResult::class.java
                )
            ).thenReturn(response)

            presenter.getPlayers(teamId)

            Mockito.verify(view).onDataLoaded(players)
        }
    }
}