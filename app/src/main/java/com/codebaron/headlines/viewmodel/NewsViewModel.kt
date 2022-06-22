package com.codebaron.headlines.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codebaron.headlines.Utilities.COUNTRY
import com.codebaron.headlines.model.News
import com.codebaron.headlines.repository.NewsRepository
import com.codebaron.headlines.roomdb.NewsRoomDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Nicholas Anyanwu
 * @since 22 Jun, 2022
 */
@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    private val _news = MutableLiveData<List<News>>()

    fun getNewsHeadline(context: Context): LiveData<List<News>> {
        viewModelScope.launch(Dispatchers.IO) {
            val news = newsRepository.getNews(COUNTRY)
            //cache new to local storage
            try {
                val localDatabase = NewsRoomDatabase(context)
                localDatabase.NewsDao().insertAllNetworkNewsHeadlines(news)
                _news.postValue(news)
            } catch (exception: Exception) {
                exception.message
            }
        }
        return _news
    }

    fun getNewsFromLocalDatabase(context: Context): List<News> {
        val localDatabase = NewsRoomDatabase(context)
        return localDatabase.NewsDao().getAllLocalNewsHeadlines()
    }
}