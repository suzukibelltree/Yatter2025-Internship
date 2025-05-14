package com.dmm.bootcamp.yatter2025.infra.api.json

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FollowUserJson(
  val id: String,
  val following: Boolean,
  @Json(name = "followed_by") val followedBy: Boolean,
)
