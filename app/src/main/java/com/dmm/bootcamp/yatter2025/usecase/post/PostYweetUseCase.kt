package com.dmm.bootcamp.yatter2025.usecase.post

import java.io.File

interface PostYweetUseCase {
  suspend fun execute(
    content: String,
    attachmentList: List<File>
  ): PostYweetUseCaseResult
}
