package com.dmm.bootcamp.yatter2025.ui.timeline

import com.dmm.bootcamp.yatter2025.ui.timeline.bindingmodel.YweetBindingModel

/**
 * タイムライン画面のUI状態
 * @param yweetList: 画面に表示するツイートのリスト
 * @param isLoading: ツイートをロード中か否か
 * @param isRefreshing: 更新処理が行われている最中か否か
 */
data class PublicTimelineUiState(
    val yweetList: List<YweetBindingModel>,
    val isLoading: Boolean,
    val isRefreshing: Boolean
) {
    companion object {
        // 画面内のツイート情報をカラにする関数
        fun empty(): PublicTimelineUiState = PublicTimelineUiState(
            yweetList = emptyList(),
            isLoading = false,
            isRefreshing = false
        )
    }
}