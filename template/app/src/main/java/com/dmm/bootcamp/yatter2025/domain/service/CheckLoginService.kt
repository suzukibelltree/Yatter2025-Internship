package com.dmm.bootcamp.yatter2025.domain.service

interface CheckLoginService {
  suspend fun execute(): Boolean
}
