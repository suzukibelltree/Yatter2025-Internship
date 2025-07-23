package com.dmm.bootcamp.yatter2025.domain.service

import com.dmm.bootcamp.yatter2025.domain.model.User

interface GetLoginUserService {
  suspend fun execute(): User?
}
