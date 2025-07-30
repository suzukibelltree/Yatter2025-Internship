package com.dmm.bootcamp.yatter2025.ui.timeline.bindingmodel

/**
 * ツイートに含まれる画像に対応したモデル
 * @param id:画像ID
 * @param type:画像のタイプ
 * @param url:画像url
 * @param description: 投稿テキスト
 */
data class ImageBindingModel(
    val id: String,
    val type: String,
    val url: String,
    val description: String?
)