package com.dmm.bootcamp.yatter2024.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.ui.register.RegisterAccountDestination
import com.dmm.bootcamp.yatter2024.ui.timeline.PublicTimelineDestination
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCase
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCaseResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class LoginViewModel(
  private val loginUseCase: LoginUseCase,
) : ViewModel() {
  private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState.empty())
  val uiState: StateFlow<LoginUiState> = _uiState

  private val _destination = MutableSharedFlow<Destination>()
  val destination: SharedFlow<Destination> = _destination.asSharedFlow()

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
        validPassword = Password(password).validate(),
        loginBindingModel = snapshotBindingModel.copy(
          password = password
        )
      )
    }
  }

  fun onClickLogin() {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true) }

      val snapBindingModel = uiState.value.loginBindingModel
      when (
        val result =
          loginUseCase.execute(
            Username(snapBindingModel.username),
            Password(snapBindingModel.password),
          )
      ) {
        is LoginUseCaseResult.Success -> {
          _destination.tryEmit(PublicTimelineDestination())
        }

        is LoginUseCaseResult.Failure -> {
          // エラー処理
          println(result)
        }
      }

      _uiState.update { it.copy(isLoading = true) }
    }
  }

  fun onClickRegister() {
    _destination.tryEmit(RegisterAccountDestination())
  }
}