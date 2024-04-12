package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.dmm.bootcamp.yatter2024.ui.LocalNavController
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ProfilePage(
  username: String?,
  viewModel: ProfileViewModel = getViewModel {
    parametersOf(username)
  },
) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  val navController = LocalNavController.current
  val lifecycleOwner = LocalLifecycleOwner.current
  LaunchedEffect(viewModel, lifecycleOwner) {
    viewModel.goBack
      .flowWithLifecycle(lifecycleOwner.lifecycle)
      .collect {
        navController.popBackStack()
      }
  }
  ProfileTemplate(
    accountBindingModel = uiState.accountBindingModel,
    statusList = uiState.statusList,
    isLoading = uiState.isLoading,
    onClickNavIcon = viewModel::onClickNavIcon,
  )
}
