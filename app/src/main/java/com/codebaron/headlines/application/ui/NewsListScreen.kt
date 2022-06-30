package com.codebaron.headlines.application.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.codebaron.headlines.R
import com.codebaron.headlines.Navigation.Destinations.DETAILS_SCREEN
import com.codebaron.headlines.Utilities.EMPTY_STRING
import com.codebaron.headlines.Utilities.NETWORK_ERROR
import com.codebaron.headlines.Utilities.NEWS_THUMBNAIL
import com.codebaron.headlines.Utilities.isNetworkAvailable
import com.codebaron.headlines.model.DummyNews
import com.codebaron.headlines.model.News
import com.codebaron.headlines.ui.theme.HeadlinesTheme
import com.codebaron.headlines.viewmodel.NewsViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * @author Nicholas Anyanwu
 * @since 22 Jun, 2022
 */

/**
 * This composable function handles fetching news data either local or from network
 * It checks if local storage is empty and network is available, if so it carries out
 * the required action to get news headlines
 * @param navController
 * @param context
 * @param viewModel
 */
@Composable
fun NewsListScreen(
    navController: NavController,
    context: Context,
    viewModel: NewsViewModel = hiltViewModel()
) {
    val localNews = viewModel.getNewsFromLocalDatabase(context)
    // condition to decide where to get news headlines from(local or remote)
    if (localNews.isEmpty() && isNetworkAvailable(context)) {
        val newsList by viewModel.getNewsHeadline(context).observeAsState(initial = emptyList())
        NewsListScreen(navController, newsList, isProgressBarVisible = false)
    } else {
        NewsListScreen(navController, localNews, isProgressBarVisible = false)
    }
}

@Composable
fun NewsListScreen(
    navController: NavController,
    news: List<News>,
    isProgressBarVisible: Boolean
) {
    val context = LocalContext.current

    Scaffold {
        Box(
            Modifier
                .padding(12.dp)
                .fillMaxSize()
                .padding(12.dp)
                .alpha(if (isProgressBarVisible) 20f else 0f),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.Gray)
        }

        LazyColumn {
            items(news) { headlines ->
                Card(
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth()
                        .clickable {
                            val encodedUrl =
                                URLEncoder.encode(headlines.url, StandardCharsets.UTF_8.toString())
                            if (!isNetworkAvailable(context)) {
                                Toast
                                    .makeText(context, NETWORK_ERROR, Toast.LENGTH_LONG)
                                    .show()
                            } else {
                                navController.navigate("${DETAILS_SCREEN}/$encodedUrl")
                            }
                        }
                ) {
                    Column {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .aspectRatio(16f / 9f),
                            painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(data = headlines.urlToImage).apply(block = fun ImageRequest.Builder.() {
                                        placeholder(R.drawable.newsicon)
                                        error(R.drawable.newsicon)
                                    }).build()
                            ),
                            contentDescription = NEWS_THUMBNAIL,
                            contentScale = ContentScale.FillWidth
                        )
                        Column(Modifier.padding(8.dp)) {
                            Text(
                                headlines.title, fontSize = 14.sp,
                                fontWeight = FontWeight.Bold, maxLines = 2
                            )
                            Text(headlines.author ?: EMPTY_STRING, maxLines = 1, fontSize = 8.sp)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsListPreview() {
    /**
     * This is just a news mock data to display a dummy UI
     */
    HeadlinesTheme {
        NewsListScreen(
            navController = rememberNavController(),
            news = DummyNews.news,
            isProgressBarVisible = false
        )
    }
}