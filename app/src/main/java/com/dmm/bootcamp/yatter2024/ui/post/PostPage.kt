package com.dmm.bootcamp.yatter2024.ui.post

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.dmm.bootcamp.yatter2024.ui.LocalNavController
import org.koin.androidx.compose.getViewModel

@Composable
fun PostPage(
  viewModel: PostViewModel = getViewModel(),
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val navController = LocalNavController.current
  val lifecycleOwner = LocalLifecycleOwner.current
  LaunchedEffect(viewModel, lifecycleOwner) {
    viewModel.destination
      .flowWithLifecycle(lifecycleOwner.lifecycle)
      .collect {
        it.navigate(navController)
      }
  }
  LifecycleEventEffect(event = Lifecycle.Event.ON_CREATE) {
    viewModel.onCreate()
  }
  PostTemplate(
    postBindingModel = uiState.bindingModel,
    isLoading = uiState.isLoading,
    canPost = uiState.canPost,
    onStatusTextChanged = viewModel::onChangedStatusText,
    onClickPost = viewModel::onClickPost,
    onClickNavIcon = viewModel::onClickNavIcon,
  )
}