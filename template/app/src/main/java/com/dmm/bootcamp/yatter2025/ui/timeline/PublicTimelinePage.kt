package com.dmm.bootcamp.yatter2025.ui.timeline

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.getViewModel

@Composable
fun PublicTimelinePage(
    publicTimeLineViewModel: PublicTimeLineViewModel = getViewModel()
) {
    val uiState by publicTimeLineViewModel.uiState.collectAsStateWithLifecycle()

    LifecycleEventEffect(
        event = Lifecycle.Event.ON_RESUME
    ) {
        publicTimeLineViewModel.onResume()
    }

    PublicTimelineTemplate(
        yweetList = uiState.yweetList,
        isLoading = uiState.isLoading,
        isRefreshing = uiState.isRefreshing,
        onRefresh = publicTimeLineViewModel::onRefresh
    )
}