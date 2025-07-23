package com.dmm.bootcamp.yatter2025.domain.service

import com.dmm.bootcamp.yatter2025.domain.model.Username

interface GetLoginUsernameService {
  fun execute(): Username?
}
