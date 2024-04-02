package com.dmm.bootcamp.yatter2024.ui.timeline

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
internal fun PublicTimelinePage(
  viewModel: PublicTimelineViewModel,
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  PublicTimelineTemplate(
    statusList = uiState.statusList,
    isLoading = uiState.isLoading,
    onClickPost = viewModel::onClickPost,
    isRefreshing = uiState.isRefreshing,
    onRefresh = viewModel::onRefresh,
    onClickProfile = viewModel::onClickProfile,
  )
}
