package com.codebaron.headlines.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.codebaron.headlines.Utilities.HEADLINE_DB
import com.codebaron.headlines.model.News

/**
 * @author Nicholas Anyanwu
 * @since 22 Jun, 2022
 */

@Database(entities = [News::class], version = 1)
abstract class NewsRoomDatabase: androidx.room.RoomDatabase() {
    abstract fun NewsDao(): NewsDao

    companion object {
        @Volatile private var instance: NewsRoomDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: NewsRoomDatabase.buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, NewsRoomDatabase::class.java, HEADLINE_DB)
                .allowMainThreadQueries()
                .build()
    }
}