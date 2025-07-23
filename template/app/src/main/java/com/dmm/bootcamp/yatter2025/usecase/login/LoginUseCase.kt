package com.dmm.bootcamp.yatter2025.usecase.login

import com.dmm.bootcamp.yatter2025.domain.model.Password
import com.dmm.bootcamp.yatter2025.domain.model.Username

interface LoginUseCase {
  suspend fun execute(
    username: Username,
    password: Password,
  ): LoginUseCaseResult
}
