package com.dmm.bootcamp.yatter2025.usecase.register

interface RegisterUserUseCase {
  suspend fun execute(
    username: String,
    password: String
  ): RegisterUserUseCaseResult
}
