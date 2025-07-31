package com.dmm.bootcamp.yatter2025.ui.timeline

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmm.bootcamp.yatter2025.ui.LocalNavController
import org.koin.androidx.compose.getViewModel

@Composable
fun PublicTimelinePage(
    publicTimeLineViewModel: PublicTimeLineViewModel = getViewModel()
) {
    val uiState by publicTimeLineViewModel.uiState.collectAsStateWithLifecycle()
    val destination by publicTimeLineViewModel.destination.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    LaunchedEffect(destination) {
        destination?.let {
            it.navigate(navController)
            publicTimeLineViewModel.onCompleteNavigation()
        }
    }
    LifecycleEventEffect(
        event = Lifecycle.Event.ON_RESUME
    ) {
        publicTimeLineViewModel.onResume()
    }

    PublicTimelineTemplate(
        yweetList = uiState.yweetList,
        isLoading = uiState.isLoading,
        isRefreshing = uiState.isRefreshing,
        onRefresh = publicTimeLineViewModel::onRefresh,
        onClickPost = publicTimeLineViewModel::onclickPost
    )
}