package com.dmm.bootcamp.yatter2025.infra.domain.converter

import com.dmm.bootcamp.yatter2025.domain.model.Image
import com.dmm.bootcamp.yatter2025.domain.model.ImageId
import com.dmm.bootcamp.yatter2025.infra.api.json.MediaJson

object MediaConverter {
  fun convertToDomainModel(jsonList: List<MediaJson>): List<Image> =
    jsonList.map { convertToDomainModel(it) }

  private fun convertToDomainModel(json: MediaJson): Image = Image(
    id = ImageId(value = json.id),
    type = json.type,
    url = json.url,
    description = json.description,
  )
}