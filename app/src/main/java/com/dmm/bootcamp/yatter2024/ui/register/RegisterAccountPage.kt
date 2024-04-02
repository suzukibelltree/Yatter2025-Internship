package com.dmm.bootcamp.yatter2024.ui.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun RegisterAccountPage(viewModel: RegisterAccountViewModel) {
  val uiState by viewModel.uiState.collectAsStateWithLifecycle()
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
