package com.codebaron.headlines.dependencyinjection

import com.codebaron.headlines.Utilities.BASE_URL
import com.codebaron.headlines.provider.NewsProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

/**
 * @author Nicholas Anyanwu
 * @since 22 Jun, 2022
 */

@Module
@InstallIn(SingletonComponent::class)
class NewsProviderRepositoryModule {

    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl() = BASE_URL.toHttpUrl()

    @Singleton
    @Provides
    fun provideRetrofit(@Named("BaseUrl") baseUrl: HttpUrl): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun providerNewsProvider(retrofit: Retrofit): NewsProvider =
        retrofit.create(NewsProvider::class.java)
}