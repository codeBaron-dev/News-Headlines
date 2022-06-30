package com.codebaron.headlines.repository

import com.codebaron.headlines.Utilities.*
import com.codebaron.headlines.model.News
import com.codebaron.headlines.provider.NewsProvider
import javax.inject.Inject

/**
 * @author Nicholas Anyanwu
 * @since 22 Jun, 2022
 */

class NewsRepositoryImplementation @Inject constructor(private val newsProvider: NewsProvider) :
    NewsRepository {

    private var news: List<News> = emptyList()

    override suspend fun getNews(): List<News> {
        val newsApiResponse = newsProvider.getNewsFeeds(NEWS_TYPE, START_DATE, SORT_BY, API_KEY).body()
        if (newsApiResponse?.status == ERROR_LOADING_FROM_REMOTE) {
            when (newsApiResponse.code) {
                API_MISSING_KEY -> throw MissingApiKeyException()
                API_KEY_INVALID -> throw ApiKeyInvalidException()
                else -> throw Exception()
            }
        }
        news = newsApiResponse?.articles ?: emptyList()
        return news
    }

    override fun getNewsHeadlines(title: String): News = news.first { it.title == title }
}