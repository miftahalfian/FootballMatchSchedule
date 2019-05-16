package com.solitelab.footballmatchschedule.data.mvp.search

import com.google.gson.Gson
import com.solitelab.footballmatchschedule.TestContextProvider
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.mvp.model.Match
import com.solitelab.footballmatchschedule.data.mvp.model.MatchResult
import com.solitelab.footballmatchschedule.data.mvp.model.SearchResult
import com.solitelab.footballmatchschedule.data.mvp.model.Team
import com.solitelab.footballmatchschedule.data.mvp.model.TeamResult
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchPresenterTest {
    @Mock
    private lateinit var matchView: MatchSearch

    @Mock
    private lateinit var teamView: TeamSearch

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: SearchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchPresenter(matchView, teamView, gson, apiRepository, TestContextProvider())
    }

    @Test
    fun search() {
        val query = "Arsenal"
        val matches : List<Match> = mutableListOf()
        val teams : List<Team> = mutableListOf()
        val matchResponse = SearchResult(matches)
        val teamResult = TeamResult(teams)

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    SearchResult::class.java
                )
            ).thenReturn(matchResponse)

            Mockito.`when`(
                gson.fromJson(
                    "",
                    TeamResult::class.java
                )
            ).thenReturn(teamResult)

            presenter.search(query)

            Mockito.verify(matchView).onDataLoaded(matches)
            Mockito.verify(teamView).onDataLoaded(teams)
        }
    }
}