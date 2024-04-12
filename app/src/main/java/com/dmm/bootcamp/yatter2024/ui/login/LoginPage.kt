package com.dmm.bootcamp.yatter2024.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.dmm.bootcamp.yatter2024.ui.LocalNavController
import org.koin.androidx.compose.getViewModel

@Composable
internal fun LoginPage(
  viewModel: LoginViewModel = getViewModel(),
) {
  val uiState: LoginUiState by viewModel.uiState.collectAsStateWithLifecycle()
  val navController = LocalNavController.current
  val lifecycleOwner = LocalLifecycleOwner.current
  LaunchedEffect(viewModel, lifecycleOwner) {
    viewModel.destination
      .flowWithLifecycle(lifecycleOwner.lifecycle)
      .collect {
        navController.popBackStack()
        it.navigate(navController)
      }
  }
  LoginTemplate(
    userName = uiState.loginBindingModel.username,
    onChangedUserName = viewModel::onChangedUsername,
    password = uiState.loginBindingModel.password,
    onChangedPassword = viewModel::onChangedPassword,
    isEnableLogin = uiState.isEnableLogin,
    isLoading = uiState.isLoading,
    onClickLogin = viewModel::onClickLogin,
    onClickRegister = viewModel::onClickRegister,
  )
}