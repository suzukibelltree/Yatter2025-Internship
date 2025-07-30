package com.dmm.bootcamp.yatter2025.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2025.domain.model.Password
import com.dmm.bootcamp.yatter2025.domain.model.Username
import com.dmm.bootcamp.yatter2025.usecase.login.LoginUseCase
import com.dmm.bootcamp.yatter2025.usecase.login.LoginUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.empty())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onChangedUsername(username: String) {
        val snapshotBindingModel = uiState.value.loginBindingModel
        _uiState.update {
            it.copy(
                validUsername = Username(username).validate(),
                loginBindingModel = snapshotBindingModel.copy(
                    username = username
                )
            )
        }
    }

    fun onChangedPassword(password: String) {
        val snapshotBindingModel = uiState.value.loginBindingModel
        _uiState.update {
            it.copy(
                validUsername = Password(password).validate(),
                loginBindingModel = snapshotBindingModel.copy(
                    password = password
                )
            )
        }
    }

    fun onClickLogin() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            val snapBindingModel = uiState.value.loginBindingModel
            when (
                val result = loginUseCase.execute(
                    Username(snapBindingModel.username),
                    Password(snapBindingModel.password)
                )
            ) {
                is LoginUseCaseResult.Success -> {
                    // パブリックタイムラインに遷移する処理
                }

                is LoginUseCaseResult.Failure -> {
                    // エラー表示
                }
            }
            _uiState.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }

    fun onClickRegister() {
        // _destination.value = RegisterUserDestination
    }

    fun onCompleteNavigation() {}
}