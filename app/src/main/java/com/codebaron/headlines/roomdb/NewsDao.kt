package com.codebaron.headlines.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codebaron.headlines.model.News

/**
 * @author Nicholas Anyanwu
 * @since 22 Jun, 2022
 */

@Dao
interface NewsDao {

    @Query("SELECT * FROM headlines_table")
    fun getAllLocalNewsHeadlines(): List<News>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllNetworkNewsHeadlines(news: List<News>)

    @Query("DELETE FROM headlines_table")
    fun deleteAll()
}