package com.solitelab.footballmatchschedule.data.mvp.leaguedetail

import com.google.gson.Gson
import com.solitelab.footballmatchschedule.TestContextProvider
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.mvp.model.LeagueDetail
import com.solitelab.footballmatchschedule.data.mvp.model.LeagueResult
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LeagueDetailPresenterTest {
    @Mock
    private lateinit var view: LeagueDetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: LeagueDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LeagueDetailPresenter(view, gson, apiRepository, TestContextProvider())
    }

    @Test
    fun getLeagueDetail() {
        val leagues: List<LeagueDetail> = mutableListOf()
        val response = LeagueResult(leagues)
        val id = "4328"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    LeagueResult::class.java
                )
            ).thenReturn(response)

            presenter.getLeagueDetail(id)

            if (leagues.isNotEmpty()) {
                Mockito.verify(view).showLeagueDetail(leagues[0])
            }
        }
    }
}