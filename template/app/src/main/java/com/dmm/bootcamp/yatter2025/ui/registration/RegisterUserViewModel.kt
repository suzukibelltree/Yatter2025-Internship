package com.dmm.bootcamp.yatter2025.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2025.common.navigation.Destination
import com.dmm.bootcamp.yatter2025.domain.model.Password
import com.dmm.bootcamp.yatter2025.domain.model.Username
import com.dmm.bootcamp.yatter2025.usecase.register.RegisterUserUseCase
import com.dmm.bootcamp.yatter2025.usecase.register.RegisterUserUseCaseResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterUserViewModel(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<RegisterUiState> =
        MutableStateFlow(RegisterUiState.empty())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

    fun onChangedUsername(username: String) {
        val snapshotBindingModel = uiState.value.userBindingModel
        _uiState.update {
            it.copy(
                validUsername = Username(username).validate(),
                userBindingModel = snapshotBindingModel.copy(
                    username = username
                )
            )
        }
    }

    fun onChangedPassword(password: String) {
        val snapshotBindingModel = uiState.value.userBindingModel
        _uiState.update {
            it.copy(
                validPassword = Password(password).validate(),
                userBindingModel = snapshotBindingModel.copy(
                    password = password
                )
            )
        }
    }

    fun onClickRegister() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            val snapBindingModel = uiState.value.userBindingModel
            when (val result = registerUserUseCase.execute(
                Username(value = snapBindingModel.username).toString(),
                Password(value = snapBindingModel.password).toString()
            )
            ) {
                is RegisterUserUseCaseResult.Success -> {
                    // タイムライン画面に遷移する
                }

                is RegisterUserUseCaseResult.Failure -> {
                    // エラー表示
                }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onCompleteNavigation() {}
}