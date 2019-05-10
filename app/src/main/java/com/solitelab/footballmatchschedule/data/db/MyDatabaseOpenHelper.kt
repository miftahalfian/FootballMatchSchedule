package com.solitelab.footballmatchschedule.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.solitelab.footballmatchschedule.data.mvp.model.Match
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteMatch.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        createLastMatchTable(db)
        createNextMatchTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable("LAST_MATCH", true)
        db.dropTable("NEXT_MATCH", true)

        createLastMatchTable(db)
        createNextMatchTable(db)
    }

    private fun createLastMatchTable(db: SQLiteDatabase) {
        db.createTable("LAST_MATCH", true,
            Match.ID to TEXT + PRIMARY_KEY,
            Match.LEAGUE to TEXT,
            Match.DATE_EVENT to TEXT,
            Match.TIME to TEXT,
            Match.HOME_TEAM_ID to TEXT,
            Match.HOME_TEAM_NAME to TEXT,
            Match.HOME_SCORE to INTEGER,
            Match.HOME_GOAL_DETAILS to TEXT,
            Match.HOME_GOAL_KEEPER to TEXT,
            Match.HOME_DEFENSE to TEXT,
            Match.HOME_MIDFIELD to TEXT,
            Match.HOME_FORWARD to TEXT,
            Match.HOME_SUBSTITUTES to TEXT,
            Match.HOME_RED_CARDS to TEXT,
            Match.HOME_YELLOW_CARDS to TEXT,
            Match.AWAY_TEAM_ID to TEXT,
            Match.AWAY_TEAM_NAME to TEXT,
            Match.AWAY_SCORE to INTEGER,
            Match.AWAY_GOAL_DETAILS to TEXT,
            Match.AWAY_GOAL_KEEPER to TEXT,
            Match.AWAY_DEFENSE to TEXT,
            Match.AWAY_MIDFIELD to TEXT,
            Match.AWAY_FORWARD to TEXT,
            Match.AWAY_SUBSTITUTES to TEXT,
            Match.AWAY_RED_CARDS to TEXT,
            Match.AWAY_YELLOW_CARDS to TEXT
        )
    }

    private fun createNextMatchTable(db: SQLiteDatabase) {
        db.createTable("NEXT_MATCH", true,
            Match.ID to TEXT + PRIMARY_KEY,
            Match.LEAGUE to TEXT,
            Match.DATE_EVENT to TEXT,
            Match.TIME to TEXT,
            Match.HOME_TEAM_ID to TEXT,
            Match.HOME_TEAM_NAME to TEXT,
            Match.HOME_SCORE to INTEGER,
            Match.HOME_GOAL_DETAILS to TEXT,
            Match.HOME_GOAL_KEEPER to TEXT,
            Match.HOME_DEFENSE to TEXT,
            Match.HOME_MIDFIELD to TEXT,
            Match.HOME_FORWARD to TEXT,
            Match.HOME_SUBSTITUTES to TEXT,
            Match.HOME_RED_CARDS to TEXT,
            Match.HOME_YELLOW_CARDS to TEXT,
            Match.AWAY_TEAM_ID to TEXT,
            Match.AWAY_TEAM_NAME to TEXT,
            Match.AWAY_SCORE to INTEGER,
            Match.AWAY_GOAL_DETAILS to TEXT,
            Match.AWAY_GOAL_KEEPER to TEXT,
            Match.AWAY_DEFENSE to TEXT,
            Match.AWAY_MIDFIELD to TEXT,
            Match.AWAY_FORWARD to TEXT,
            Match.AWAY_SUBSTITUTES to TEXT,
            Match.AWAY_RED_CARDS to TEXT,
            Match.AWAY_YELLOW_CARDS to TEXT
        )
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)