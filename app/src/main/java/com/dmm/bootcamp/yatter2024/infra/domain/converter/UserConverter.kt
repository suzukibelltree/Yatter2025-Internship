package com.dmm.bootcamp.yatter2024.infra.domain.converter

import com.dmm.bootcamp.yatter2024.domain.model.User
import com.dmm.bootcamp.yatter2024.domain.model.UserId
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.infra.api.json.UserJson
import java.net.URL

object UserConverter {
  fun convertToDomainModel(
    jsonList: List<UserJson>,
  ): List<User> = jsonList.map { convertToDomainModel(it) }

  fun convertToDomainModel(json: UserJson): User = User(
    id = UserId(json.id),
    username = Username(json.username),
    displayName = json.displayName,
    note = json.note,
    avatar = URL(json.avatar),
    header = URL(json.header),
    followingCount = json.followingCount,
    followerCount = json.followersCount,
  )
}
