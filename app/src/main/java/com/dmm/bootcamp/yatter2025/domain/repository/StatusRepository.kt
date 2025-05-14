package com.dmm.bootcamp.yatter2025.domain.repository

import com.dmm.bootcamp.yatter2025.domain.model.Status
import com.dmm.bootcamp.yatter2025.domain.model.StatusId
import java.io.File

interface StatusRepository {
  suspend fun findById(id: StatusId): Status?

  suspend fun findAllPublic(): List<Status>

  suspend fun findAllHome(): List<Status>

  suspend fun create(
    content: String,
    attachmentList: List<File>
  ): Status

  suspend fun delete(
    status: Status
  )
}
