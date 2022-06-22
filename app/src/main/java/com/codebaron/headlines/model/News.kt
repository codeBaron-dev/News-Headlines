package com.codebaron.headlines.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.codebaron.headlines.Utilities.HEADLINE_TABLE

/**
 * @author Nicholas Anyanwu
 * @since 22 Jun, 2022
 */

@Entity(tableName = HEADLINE_TABLE)
data class News(
    @PrimaryKey @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "content") var content: String?,
    @ColumnInfo(name = "author") var author: String?,
    @ColumnInfo(name = "url") var url: String,
    @ColumnInfo(name = "urlToImage") var urlToImage: String,
)