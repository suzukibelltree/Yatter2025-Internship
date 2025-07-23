package com.dmm.bootcamp.yatter2025.infra.api.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class YweetJson(
  @Json(name = "id") val id: String,
  @Json(name = "user") val user: UserJson,
  @Json(name = "content") val content: String?,
  @Json(name = "created_at") val createdAt: String,
  @Json(name = "image_attachments") val attachmentImageList: List<ImageJson>
)
