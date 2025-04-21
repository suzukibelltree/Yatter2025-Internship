package com.dmm.bootcamp.yatter2024.domain.service

import com.dmm.bootcamp.yatter2024.domain.model.Account

interface GetLoginAccountService {
  suspend fun execute(): Account?
}
