package com.dmm.bootcamp.yatter2025.infra.domain.repository

import com.dmm.bootcamp.yatter2025.domain.model.Yweet
import com.dmm.bootcamp.yatter2025.domain.model.YweetId
import com.dmm.bootcamp.yatter2025.domain.repository.YweetRepository
import com.dmm.bootcamp.yatter2025.auth.TokenProvider
import com.dmm.bootcamp.yatter2025.infra.api.YatterApi
import com.dmm.bootcamp.yatter2025.infra.api.json.PostYweetJson
import com.dmm.bootcamp.yatter2025.infra.domain.converter.StatusConverter
import com.dmm.bootcamp.yatter2025.infra.pref.LoginUserPreferences
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import java.io.File

class YweetRepositoryImpl(
  private val yatterApi: YatterApi,
  private val tokenProvider: TokenProvider,
  private val loginUserPreferences: LoginUserPreferences,
) : YweetRepository {
  override suspend fun findById(id: YweetId): Yweet? {
    TODO("Not yet implemented")
  }

  override suspend fun findAllPublic(): List<Yweet> = withContext(IO) {
    val loginUsername = loginUserPreferences.getUsername()
    val statusList = yatterApi.getPublicTimeline()
    StatusConverter.convertToDomainModel(statusList, loginUsername = loginUsername)
  }

  override suspend fun findAllHome(): List<Yweet> = withContext(IO) {
    val loginUsername = loginUserPreferences.getUsername()
    val statusList = yatterApi.getHomeTimeline(tokenProvider.provide())
    StatusConverter.convertToDomainModel(statusList, loginUsername)
  }

  override suspend fun create(
    content: String,
    attachmentList: List<File>
  ): Yweet = withContext(IO) {
    val statusJson = yatterApi.postStatus(
      tokenProvider.provide(),
      PostYweetJson(
        content,
        listOf()
      )
    )
    StatusConverter.convertToDomainModel(statusJson, isMe = true)
  }

  override suspend fun delete(yweet: Yweet) {
    TODO("Not yet implemented")
  }
}
