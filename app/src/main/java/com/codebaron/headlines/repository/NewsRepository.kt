package com.codebaron.headlines.repository

import com.codebaron.headlines.model.News

/**
 * @author Nicholas Anyanwu
 * @since 22 Jun, 2022
 */

interface NewsRepository {
    suspend fun getNews(country: String): List<News>
    fun getNewsHeadlines(title: String): News
}