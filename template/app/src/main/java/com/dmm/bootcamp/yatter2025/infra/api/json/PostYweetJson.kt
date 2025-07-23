package com.dmm.bootcamp.yatter2025.infra.api.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostYweetJson(
  val yweet: String,
  @Json(name = "images") val imageList: List<PostYweetImageJson>,
)
