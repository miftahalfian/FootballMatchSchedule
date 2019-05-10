package com.solitelab.footballmatchschedule.data.mvp.favorite

import android.content.Context
import com.solitelab.footballmatchschedule.data.mvp.model.Match
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class FavoriteMatchPresenterTest {
    @Mock
    private lateinit var view: FavoriteMatchView

    private lateinit var context: Context

    private lateinit var presenter: FavoriteMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = FavoriteMatchPresenter(view)
    }

    @Test
    fun loadLastMatchFavorite() {
        val matches : List<Match> = mutableListOf()

        runBlocking {
            presenter.loadLastMatchFavorite(context)

            Mockito.verify(view).onLoadData()
            Mockito.verify(view).onDataLoaded(matches)
        }
    }

    @Test
    fun loadNextMatchFavorite() {
    }
}