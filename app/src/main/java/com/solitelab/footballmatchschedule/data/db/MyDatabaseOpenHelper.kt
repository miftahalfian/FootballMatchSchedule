package com.solitelab.footballmatchschedule.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.solitelab.footballmatchschedule.data.mvp.model.Match
import com.solitelab.footballmatchschedule.data.mvp.model.Team
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteMatch.db", null, 2) {
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
        createTeamTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        dropLastMatchTable(db)
        dropNextMatchTable(db)
        dropTeamTable(db)
        createLastMatchTable(db)
        createNextMatchTable(db)
        createTeamTable(db)
    }

    private fun createLastMatchTable(db: SQLiteDatabase) {
        db.createTable("LAST_MATCH", true,
            Match.ID to TEXT + PRIMARY_KEY,
            Match.LEAGUE to TEXT,
            Match.SPORT to TEXT,
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

    private fun dropLastMatchTable(db: SQLiteDatabase) {
        db.dropTable("LAST_MATCH", true)
    }

    private fun createNextMatchTable(db: SQLiteDatabase) {
        db.createTable("NEXT_MATCH", true,
            Match.ID to TEXT + PRIMARY_KEY,
            Match.LEAGUE to TEXT,
            Match.SPORT to TEXT,
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

    private fun dropNextMatchTable(db: SQLiteDatabase) {
        db.dropTable("NEXT_MATCH", true)
    }

    private fun createTeamTable(db: SQLiteDatabase) {
        db.createTable("TEAM", true,
            Team.ID_TEAM to TEXT + PRIMARY_KEY,
            Team.TEAM_NAME to TEXT,
            Team.COUNTRY to TEXT,
            Team.SPORT to TEXT,
            Team.BADGE to TEXT,
            Team.STADIUM to TEXT,
            Team.STADIUM_THUMB to TEXT,
            Team.STADIUM_LOCATION to TEXT,
            Team.STADIUM_CAPACITY to TEXT,
            Team.STADIUM_DESCRIPTION to TEXT,
            Team.DESCRIPTION to TEXT
        )
    }

    private fun dropTeamTable(db: SQLiteDatabase) {
        db.dropTable("TEAM", true)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)