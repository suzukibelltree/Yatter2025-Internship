package com.dmm.bootcamp.yatter2024.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.common.navigation.PopBackDestination
import com.dmm.bootcamp.yatter2024.domain.service.GetMeService
import com.dmm.bootcamp.yatter2024.usecase.post.PostStatusUseCase
import com.dmm.bootcamp.yatter2024.usecase.post.PostStatusUseCaseResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostViewModel(
  private val postStatusUseCase: PostStatusUseCase,
  private val getMeService: GetMeService,
) : ViewModel() {
  private val _uiState: MutableStateFlow<PostUiState> = MutableStateFlow(PostUiState.empty())
  val uiState: StateFlow<PostUiState> = _uiState

  private val _destination = MutableSharedFlow<Destination>()
  val destination: SharedFlow<Destination> = _destination.asSharedFlow()

  fun onCreate() {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true) }

      val me = getMeService.execute()

      val snapshotBindingModel = uiState.value.bindingModel
      _uiState.update {
        it.copy(
          bindingModel = snapshotBindingModel.copy(avatarUrl = me?.avatar.toString()),
          isLoading = false,
        )
      }
    }
  }

  fun onChangedStatusText(statusText: String) {
    _uiState.update { it.copy(bindingModel = uiState.value.bindingModel.copy(statusText = statusText)) }
  }

  fun onClickPost() {
    viewModelScope.launch {
      _uiState.update { it.copy(isLoading = true) }

      val result = postStatusUseCase.execute(
        content = uiState.value.bindingModel.statusText,
        attachmentList = listOf()
      )
      when (result) {
        PostStatusUseCaseResult.Success -> {
          _destination.tryEmit(PopBackDestination)
        }

        is PostStatusUseCaseResult.Failure -> {
          // エラー表示
        }
      }
      _uiState.update { it.copy(isLoading = false) }
    }
  }

  fun onClickNavIcon() {
    _destination.tryEmit(PopBackDestination)
  }
}