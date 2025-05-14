package com.dmm.bootcamp.yatter2025.infra.domain.converter

import com.dmm.bootcamp.yatter2025.domain.model.Yweet
import com.dmm.bootcamp.yatter2025.domain.model.YweetId
import com.dmm.bootcamp.yatter2025.infra.api.json.YweetJson

object YweetConverter {
  fun convertToDomainModel(jsonList: List<YweetJson>, loginUsername: String?): List<Yweet> =
    jsonList.map {
      convertToDomainModel(it, isMe = it.user.username == loginUsername)
    }

  fun convertToDomainModel(json: YweetJson, isMe: Boolean): Yweet = Yweet(
    id = YweetId(json.id),
    user = UserConverter.convertToDomainModel(json.user, isMe = isMe),
    content = json.content ?: "",
    attachmentImageList =  ImageConverter.convertToDomainModel(json.attachmentImageList)
  )
}
