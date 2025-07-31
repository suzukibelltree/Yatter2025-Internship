package com.dmm.bootcamp.yatter2025.ui.timeline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2025.common.navigation.Destination
import com.dmm.bootcamp.yatter2025.domain.repository.YweetRepository
import com.dmm.bootcamp.yatter2025.ui.post.PostDestination
import com.dmm.bootcamp.yatter2025.ui.timeline.bindingmodel.converter.YeetConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PublicTimeLineViewModel(
    private val yweetRepository: YweetRepository
) : ViewModel() {
    // UIStateをStateFlowとしてコンポーザブル関数側に流している
    private val _uiState: MutableStateFlow<PublicTimelineUiState> = MutableStateFlow(
        PublicTimelineUiState.empty()
    )
    val uiState: StateFlow<PublicTimelineUiState> = _uiState.asStateFlow()

    private val _destination = MutableStateFlow<Destination?>(null)
    val destination: StateFlow<Destination?> = _destination.asStateFlow()

    // リポジトリを介してツイート情報を取得し、それに伴ってUI状態を更新する
    private suspend fun fetchPublicTimeline() {
        val yweetList = yweetRepository.findAllPublic()
        _uiState.update {
            it.copy(
                yweetList = YeetConverter.convertToBindingModel(yweetList)
            )
        }
    }

    //アプリ起動時にツイートを取得
    fun onResume() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            fetchPublicTimeline()
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    //画面リフレッシュしたい時に呼び出す関数
    fun onRefresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }
            fetchPublicTimeline()
            _uiState.update { it.copy(isRefreshing = false) }
        }
    }

    fun onclickPost() {
        _destination.value = PostDestination()
    }

    fun onCompleteNavigation() {
        _destination.value = null
    }
}