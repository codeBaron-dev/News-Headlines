package com.codebaron.headlines.utils

import com.codebaron.headlines.dependencyinjection.NewsProviderModule
import com.codebaron.headlines.model.News
import com.codebaron.headlines.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

/**
 * @author Nicholas Anyanwu
 * @since 22 Jun, 2022
 */

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NewsProviderModule::class]
)
class TestRepositoryModule {

    @Provides
    @Singleton
    fun providerNewsRepository(): NewsRepository =
        object : NewsRepository {
            val news = arrayListOf(
                News("Title1", "Content1", "Author1", "Url1", "urlImage1"),
                News("Title2", "Content2", "Author2", "Url2", "urlImage2")
            )

            override suspend fun getNews(country: String): List<News> = news
            override fun getNewsHeadlines(title: String): News = news[0]
        }
}