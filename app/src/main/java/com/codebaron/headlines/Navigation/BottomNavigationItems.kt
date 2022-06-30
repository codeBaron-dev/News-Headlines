package com.codebaron.headlines.Navigation

import com.codebaron.headlines.Navigation.Destinations.FAVOURITE_SCREEN
import com.codebaron.headlines.Navigation.Destinations.LIST_SCREEN
import com.codebaron.headlines.Navigation.Destinations.SETTINGS_SCREEN
import com.codebaron.headlines.R
import com.codebaron.headlines.Utilities.FAVOURITE
import com.codebaron.headlines.Utilities.NEWS_HEADLINE
import com.codebaron.headlines.Utilities.SETTING

sealed class BottomNavigationItems(
    var title: String,
    var icon: Int,
    var destinations: String
) {

    object Headlines : BottomNavigationItems(
        NEWS_HEADLINE,
        R.drawable.ic_baseline_wifi_tethering_24,
        LIST_SCREEN
    )

    object Favourites : BottomNavigationItems(
        FAVOURITE,
        R.drawable.ic_baseline_favorite_24,
        FAVOURITE_SCREEN
    )

    object Settings : BottomNavigationItems(
        SETTING,
        R.drawable.ic_baseline_settings_24,
        SETTINGS_SCREEN
    )
}