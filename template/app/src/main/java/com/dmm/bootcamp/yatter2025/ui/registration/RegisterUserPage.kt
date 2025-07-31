package com.dmm.bootcamp.yatter2025.ui.registration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dmm.bootcamp.yatter2025.ui.LocalNavController
import org.koin.androidx.compose.getViewModel

@Composable
fun RegisterUserPage(
    registerUserViewModel: RegisterUserViewModel = getViewModel()
) {
    val uiState: RegisterUiState by registerUserViewModel.uiState.collectAsStateWithLifecycle()
    val destination by registerUserViewModel.destination.collectAsStateWithLifecycle()
    val navController = LocalNavController.current
    LaunchedEffect(destination) {
        destination?.let {
            it.navigate(navController)
            registerUserViewModel.onCompleteNavigation()
        }
    }
    RegisterTemplate(
        userName = uiState.userBindingModel.username,
        onChangedUserName = registerUserViewModel::onChangedUsername,
        password = uiState.userBindingModel.password,
        onChangedPassword = registerUserViewModel::onChangedPassword,
        isEnableRegister = uiState.isEnableRegister,
        isLoading = uiState.isLoading,
        onClickRegister = registerUserViewModel::onClickRegister,
        errorMessage = uiState.errorMessage
    )
}