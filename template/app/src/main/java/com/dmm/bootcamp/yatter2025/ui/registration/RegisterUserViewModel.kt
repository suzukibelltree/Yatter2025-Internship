package com.dmm.bootcamp.yatter2025.ui.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2025.common.navigation.Destination
import com.dmm.bootcamp.yatter2025.domain.model.Password
import com.dmm.bootcamp.yatter2025.domain.model.Username
import com.dmm.bootcamp.yatter2025.ui.timeline.PublicTimelineDestination
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
        checkValidOrNot(username, snapshotBindingModel.password)
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
        checkValidOrNot(snapshotBindingModel.username, password)
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
                    _destination.value = RegisterUserDestination()
                }

                is RegisterUserUseCaseResult.Failure -> {

                }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
        _destination.value = PublicTimelineDestination()
    }

    fun checkValidOrNot(userName: String, password: String) {
        viewModelScope.launch {
            if (!Password(password).validate()) {
                _uiState.update {
                    it.copy(
                        errorMessage = "パスワードは大文字・小文字・記号を含む8文字以上にしてください"
                    )
                }
            } else if (!Username(userName).validate()) {
                _uiState.update {
                    it.copy(
                        errorMessage = "ユーザー名が空になっています"
                    )
                }
            }
        }
    }

    fun onCompleteNavigation() {
        _destination.value = null
    }
}