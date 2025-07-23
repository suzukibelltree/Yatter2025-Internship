package com.dmm.bootcamp.yatter2025.usecase.post

sealed interface PostYweetUseCaseResult {
  object Success : PostYweetUseCaseResult
  sealed interface Failure : PostYweetUseCaseResult {
    object EmptyContent : Failure
    object NotLoggedIn : Failure
    data class OtherError(val throwable: Throwable) : Failure
  }
}
