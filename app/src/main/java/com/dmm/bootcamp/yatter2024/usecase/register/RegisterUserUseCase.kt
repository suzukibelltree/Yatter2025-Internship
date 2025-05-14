package com.dmm.bootcamp.yatter2024.usecase.register

interface RegisterUserUseCase {
  suspend fun execute(
    username: String,
    password: String
  ): RegisterUserUseCaseResult
}
