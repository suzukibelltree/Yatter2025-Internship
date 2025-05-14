package com.dmm.bootcamp.yatter2025.domain.model

import com.dmm.bootcamp.yatter2025.common.ddd.Identifier

class Username(value: String): Identifier<String>(value) {
  fun validate(): Boolean = value.isNotBlank()
}
