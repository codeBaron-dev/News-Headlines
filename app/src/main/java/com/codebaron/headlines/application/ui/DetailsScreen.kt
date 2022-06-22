package com.codebaron.headlines.application.ui

import android.content.Context
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.codebaron.headlines.Utilities.DUMMY_URL
import com.codebaron.headlines.ui.theme.HeadlinesTheme

/**
 * @author Nicholas Anyanwu
 * @since 22 Jun, 2022
 */

@Composable
fun NewsDetails(
    url: String,
    context: Context,
) {
    NewsDetailsScreen(url, context)
}

@Composable
fun NewsDetailsScreen(
    url: String,
    context: Context?
) {
    Box(
        Modifier
            .padding(12.dp)
            .fillMaxSize()
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.Gray)
    }

    AndroidView(factory = {
        WebView(context!!).apply {
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    })
}

@Preview(showBackground = true)
@Composable
fun DetailsPreview() {
    HeadlinesTheme {
        NewsDetailsScreen(
            url = DUMMY_URL,
            context = null
        )
    }
}