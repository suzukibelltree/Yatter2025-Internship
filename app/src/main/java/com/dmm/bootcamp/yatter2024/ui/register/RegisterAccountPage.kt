package com.dmm.bootcamp.yatter2024.ui.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import com.dmm.bootcamp.yatter2024.ui.LocalNavController
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterAccountPage(
  viewModel: RegisterAccountViewModel = getViewModel(),
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
  RegisterAccountTemplate(
    userName = uiState.bindingModel.userName,
    onChangedUserName = viewModel::onChangedUserName,
    password = uiState.bindingModel.password,
    onChangedPassword = viewModel::onChangedPassword,
    isLoading = uiState.isLoading,
    onClickRegister = viewModel::onClickRegister,
    onClickLogin = viewModel::onClickLogin,
  )
}
