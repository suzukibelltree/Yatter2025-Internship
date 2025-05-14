package com.dmm.bootcamp.yatter2024.domain.service

import com.dmm.bootcamp.yatter2024.domain.model.User

interface GetLoginUserService {
  suspend fun execute(): User?
}
