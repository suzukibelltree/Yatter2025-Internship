package com.dmm.bootcamp.yatter2025.domain.model

import com.dmm.bootcamp.yatter2025.common.ddd.Entity

class Status(
  id: StatusId,
  val user: User,
  val content: String,
  val attachmentMediaList: List<Media>
) : Entity<StatusId>(id)
