package com.dmm.bootcamp.yatter2025.usecase.impl.post

import android.accounts.AuthenticatorException
import com.dmm.bootcamp.yatter2025.domain.repository.YweetRepository
import com.dmm.bootcamp.yatter2025.usecase.post.PostYweetUseCase
import com.dmm.bootcamp.yatter2025.usecase.post.PostYweetUseCaseResult
import java.io.File

class PostYweetUseCaseImpl(
  private val yweetRepository: YweetRepository
) : PostYweetUseCase {
  override suspend fun execute(
    content: String,
    attachmentList: List<File>
  ): PostYweetUseCaseResult {
    if (content == "" && attachmentList.isEmpty()) {
      return PostYweetUseCaseResult.Failure.EmptyContent
    }

    return try {
      yweetRepository.create(
        content = content,
        attachmentList = emptyList()
      )

      PostYweetUseCaseResult.Success
    } catch (e: AuthenticatorException) {
      PostYweetUseCaseResult.Failure.NotLoggedIn
    } catch (e: Exception) {
      PostYweetUseCaseResult.Failure.OtherError(e)
    }
  }
}
