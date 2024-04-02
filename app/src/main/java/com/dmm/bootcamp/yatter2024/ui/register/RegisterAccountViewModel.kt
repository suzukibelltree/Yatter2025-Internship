package com.dmm.bootcamp.yatter2024.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.usecase.register.RegisterAccountUseCase
import com.dmm.bootcamp.yatter2024.usecase.register.RegisterAccountUseCaseResult
import com.dmm.bootcamp.yatter2024.util.SingleLiveEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterAccountViewModel(
  private val registerAccountUseCase: RegisterAccountUseCase,
) : ViewModel() {
  private val _uiState: MutableStateFlow<RegisterAccountUiState> =
    MutableStateFlow(RegisterAccountUiState.empty())
  val uiState: StateFlow<RegisterAccountUiState> = _uiState

  private val _navigateToAllTimeLine: SingleLiveEvent<Unit> = SingleLiveEvent()
   val navigateToAllTimeLine: LiveData<Unit> = _navigateToAllTimeLine

  private val _navigateToLogin: SingleLiveEvent<Unit> = SingleLiveEvent()
  val navigateToLogin: LiveData<Unit> = _navigateToLogin

  fun onClickRegister() {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true) }

      val snapBindingModel = uiState.value.bindingModel

      when (
        val result =
          registerAccountUseCase.execute(snapBindingModel.userName, snapBindingModel.password)
      ) {
        is RegisterAccountUseCaseResult.Success -> {
          _navigateToAllTimeLine.value = Unit
        }

        is RegisterAccountUseCaseResult.Failure -> {
          println(result)
        }
      }

      _uiState.update { it.copy(isLoading = false) }
    }
  }

  fun onClickLogin() {
    _navigateToLogin.value = Unit
  }

  fun onChangedUserName(userName: String) {
    val snapBindingModel = uiState.value.bindingModel
    _uiState.update { it.copy(bindingModel = snapBindingModel.copy(userName = userName)) }
  }

  fun onChangedPassword(password: String) {
    val snapBindingModel = uiState.value.bindingModel
    _uiState.update { it.copy(bindingModel = snapBindingModel.copy(password = password)) }
  }
}
