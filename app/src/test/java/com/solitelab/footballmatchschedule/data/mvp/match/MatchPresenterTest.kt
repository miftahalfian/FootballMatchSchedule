package com.solitelab.footballmatchschedule.data.mvp.match

import com.google.gson.Gson
import com.solitelab.footballmatchschedule.TestContextProvider
import com.solitelab.footballmatchschedule.data.api.ApiRepository
import com.solitelab.footballmatchschedule.data.mvp.model.Match
import com.solitelab.footballmatchschedule.data.mvp.model.MatchResult
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchPresenterTest {
    @Mock
    private lateinit var view: MatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: MatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, gson, apiRepository, TestContextProvider())
    }

    @Test
    fun getLastMatch() {
        val matches : List<Match> = mutableListOf()
        val response = MatchResult(matches)
        val id = 4328

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    MatchResult::class.java
                )
            ).thenReturn(response)

            presenter.getLastMatch(id)

            Mockito.verify(view).onDataLoaded(matches, "LastMatch")
        }
    }

    @Test
    fun getNextMatch() {
        val matches : List<Match> = mutableListOf()
        val response = MatchResult(matches)
        val id = 4328

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    MatchResult::class.java
                )
            ).thenReturn(response)

            presenter.getNextMatch(id)

            Mockito.verify(view).onDataLoaded(matches, "NextMatch")
        }
    }
}