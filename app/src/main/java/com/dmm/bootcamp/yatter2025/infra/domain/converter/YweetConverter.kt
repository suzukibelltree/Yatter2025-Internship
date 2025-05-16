package com.dmm.bootcamp.yatter2025.infra.domain.converter

import com.dmm.bootcamp.yatter2025.domain.model.Yweet
import com.dmm.bootcamp.yatter2025.domain.model.YweetId
import com.dmm.bootcamp.yatter2025.infra.api.json.YweetJson

object YweetConverter {
  fun convertToDomainModel(jsonList: List<YweetJson>): List<Yweet> =
    jsonList.map {
      convertToDomainModel(it)
    }

  fun convertToDomainModel(json: YweetJson): Yweet = Yweet(
    id = YweetId(json.id),
    user = UserConverter.convertToDomainModel(json.user),
    content = json.content ?: "",
    attachmentImageList =  ImageConverter.convertToDomainModel(json.attachmentImageList)
  )
}
