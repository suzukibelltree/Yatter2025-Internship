package com.dmm.bootcamp.yatter2025.infra.domain.converter

import com.dmm.bootcamp.yatter2025.domain.model.User
import com.dmm.bootcamp.yatter2025.domain.model.UserId
import com.dmm.bootcamp.yatter2025.domain.model.Username
import com.dmm.bootcamp.yatter2025.infra.api.json.UserJson
import java.net.URL

object UserConverter {
  fun convertToDomainModel(json: UserJson, isMe: Boolean) = User(
    id = UserId(json.id),
    username = Username(json.username),
    displayName = json.displayName,
    note = json.note,
    avatar = URL(json.avatar),
    header = URL(json.header),
    followingCount = json.followingCount,
    followerCount = json.followersCount,
    isMe = isMe,
  )
}
