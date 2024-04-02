package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ProfilePage(viewModel: ProfileViewModel) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
  ProfileTemplate(
    accountBindingModel = uiState.accountBindingModel,
    statusList = uiState.statusList,
    isLoading = uiState.isLoading,
    onClickNavIcon = viewModel::onClickNavIcon,
  )
}
