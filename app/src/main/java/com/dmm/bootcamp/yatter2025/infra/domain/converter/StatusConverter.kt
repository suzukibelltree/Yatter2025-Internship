package com.dmm.bootcamp.yatter2025.infra.domain.converter

import com.dmm.bootcamp.yatter2025.domain.model.Status
import com.dmm.bootcamp.yatter2025.domain.model.StatusId
import com.dmm.bootcamp.yatter2025.infra.api.json.StatusJson

object StatusConverter {
  fun convertToDomainModel(jsonList: List<StatusJson>, loginUsername: String?): List<Status> =
    jsonList.map {
      convertToDomainModel(it, isMe = it.user.username == loginUsername)
    }

  fun convertToDomainModel(json: StatusJson, isMe: Boolean): Status = Status(
    id = StatusId(json.id),
    user = UserConverter.convertToDomainModel(json.user, isMe = isMe),
    content = json.content ?: "",
    attachmentMediaList =  MediaConverter.convertToDomainModel(json.attachmentMediaList)
  )
}
