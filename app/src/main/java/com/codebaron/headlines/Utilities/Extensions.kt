package com.codebaron.headlines.Utilities

import android.content.Context
import android.net.ConnectivityManager

/**
 * @author Nicholas Anyanwu
 * @since 22 Jun, 2022
 */

fun isNetworkAvailable(context: Context): Boolean {
    val connectMgr: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectMgr.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}