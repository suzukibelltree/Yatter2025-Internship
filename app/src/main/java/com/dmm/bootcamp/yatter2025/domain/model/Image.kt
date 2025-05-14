package com.dmm.bootcamp.yatter2025.domain.model

import com.dmm.bootcamp.yatter2025.common.ddd.Entity

class Image(
  id: ImageId,
  val type: String,
  val url: String,
  val description: String?,
) : Entity<ImageId>(id)
