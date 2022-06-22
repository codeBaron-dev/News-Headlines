package com.codebaron.headlines

import com.codebaron.headlines.Utilities.ApiKeyInvalidException
import com.codebaron.headlines.Utilities.MissingApiKeyException
import com.codebaron.headlines.provider.NewsProvider
import com.codebaron.headlines.repository.NewsRepositoryImplementation
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets

/**
 * @author Nicholas Anyanwu
 * @since 22 Jun, 2022
 */

class NewsRepositoryTest {

    private val mockWebServer = MockWebServer()

    private val newsProvider = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsProvider::class.java)

    private val newsRepository = NewsRepositoryImplementation(newsProvider)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Top headlines response is correct`() {
        mockWebServer.enqueueResponse("top_headlines.json")

        runBlocking {
            val articles = newsRepository.getNews("US")
            assertEquals(2, articles.size)
            assertEquals("Sophie Lewis", articles[0].author)
            assertEquals("KOCO Staff", articles[1].author)
        }
    }

    @Test
    fun `Api key missing exception`() {
        mockWebServer.enqueueResponse("api_key_missing.json")
        assertThrows(MissingApiKeyException::class.java) {
            runBlocking {
                newsRepository.getNews("US")
            }
        }
    }

    @Test
    fun `Invalid Api Key exception`() {
        mockWebServer.enqueueResponse("api_key_invalid.json")
        assertThrows(ApiKeyInvalidException::class.java) {
            runBlocking {
                newsRepository.getNews("US")
            }
        }
    }
}

fun MockWebServer.enqueueResponse(filePath: String) {
    val inputStream = javaClass.classLoader?.getResourceAsStream(filePath)
    val source = inputStream?.source()?.buffer()
    source?.let {
        enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(it.readString(StandardCharsets.UTF_8))
        )
    }
}