package com.codebaron.headlines.Utilities

import com.codebaron.headlines.Navigation.BottomNavigationItems

/**
 * @author Nicholas Anyanwu
 * @since 22 Jun, 2022
 */

const val BASE_URL = "https://newsapi.org/v2/"
const val API_KEY = "37effe73b6094b3f99ccc89bbc5d7ed6"
const val END_POINT = "everything"
const val NEWS_TYPE = "tesla"
const val START_DATE = "2022-05-30"
const val SORT_BY = "publishedAt"

const val APP_BAR_TITLE = "News Feeds"
const val NETWORK_ERROR = "Ensure you have a stable internet connection to load this news"
const val ERROR_LOADING_FROM_REMOTE = "error"
const val DISCONNECTED = "Network Error, some content may not load properly"

const val NEWS_THUMBNAIL = "News Thumbnail"
const val DUMMY_URL = "dummy url"
const val EMPTY_STRING = ""

const val API_MISSING_KEY = "apiKeyMissing"
const val API_KEY_INVALID = "apiKeyInvalid"

const val HEADLINE_DB = "headlines.db"
const val HEADLINE_TABLE = "headlines_table"
const val HEADLINE_URL = "headlinesUrl"

const val COUNTRY = "US"
const val TEST_STRING = "test"

const val FAVOURITE = "Favourite"
const val SETTING = "Settings"
const val NEWS_HEADLINE = "News Headlines"
val NAVIGATION_OBJECTS = listOf(BottomNavigationItems.Headlines,
    BottomNavigationItems.Favourites, BottomNavigationItems.Settings
)