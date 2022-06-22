package com.codebaron.headlines.dependencyinjection

import com.codebaron.headlines.provider.NewsProvider
import com.codebaron.headlines.repository.NewsRepository
import com.codebaron.headlines.repository.NewsRepositoryImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Nicholas Anyanwu
 * @since 22 Jun, 2022
 */

@Module
@InstallIn(SingletonComponent::class)
class NewsProviderModule {

    @Provides
    @Singleton
    fun providerNewsRepository(provider: NewsProvider): NewsRepository =
        NewsRepositoryImplementation(provider)
}