package com.codebaron.headlines.provider

import com.codebaron.headlines.Utilities.END_POINT
import com.codebaron.headlines.model.NewsApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Nicholas Anyanwu
 * @since 22 Jun, 2022
 */

interface NewsProvider {

    @GET(END_POINT)
    suspend fun getNewsFeeds(@Query("country") country: String): Response<NewsApiResponse>
}