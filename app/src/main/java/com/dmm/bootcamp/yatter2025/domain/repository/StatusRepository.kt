package com.dmm.bootcamp.yatter2025.domain.repository

import com.dmm.bootcamp.yatter2025.domain.model.Yweet
import com.dmm.bootcamp.yatter2025.domain.model.YweetId
import java.io.File

interface StatusRepository {
  suspend fun findById(id: YweetId): Yweet?

  suspend fun findAllPublic(): List<Yweet>

  suspend fun findAllHome(): List<Yweet>

  suspend fun create(
    content: String,
    attachmentList: List<File>
  ): Yweet

  suspend fun delete(
    yweet: Yweet
  )
}
