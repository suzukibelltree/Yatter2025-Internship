package com.dmm.bootcamp.yatter2024.usecase.register

sealed class RegisterAccountUseCaseResult {
  object Success : RegisterAccountUseCaseResult()
  sealed class Failure : RegisterAccountUseCaseResult() {
    object EmptyUsername : Failure()
    object EmptyPassword : Failure()
    object InvalidPassword : Failure()
  }
}
