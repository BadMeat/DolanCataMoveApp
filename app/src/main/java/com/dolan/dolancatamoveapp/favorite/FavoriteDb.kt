package com.dolan.dolancatamoveapp.favorite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class FavoriteDb(context: Context) : ManagedSQLiteOpenHelper(context, "Favorite.db", null, 1) {

    companion object {
        private var instance: FavoriteDb? = null

        fun getInstance(context: Context): FavoriteDb {
            if (instance == null) {
                instance = FavoriteDb(context)
            }
            return instance as FavoriteDb
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            Favorite.TABLE_NAME, true,
            Favorite.FAV_ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorite.FAV_NAME to TEXT,
            Favorite.FAV_RATE to REAL,
            Favorite.FAV_DETAIL to TEXT,
            Favorite.FAV_POSTER to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Favorite.TABLE_NAME)
    }
}

val Context.database: FavoriteDb
    get() = FavoriteDb.getInstance(applicationContext)