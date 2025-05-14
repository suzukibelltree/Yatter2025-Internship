package com.dmm.bootcamp.yatter2025.infra.api.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostYweetImageJson(
  @Json(name = "image_id") val imageId: Int,
  val description: String,
)
