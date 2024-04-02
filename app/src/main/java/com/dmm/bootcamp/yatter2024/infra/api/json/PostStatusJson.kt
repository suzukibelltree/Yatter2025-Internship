package com.dmm.bootcamp.yatter2024.infra.api.json

import com.squareup.moshi.Json

data class PostStatusJson(
  val status: String,
  @Json(name = "media_ids") val mediaIds: List<Int>
)
