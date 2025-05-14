package com.dmm.bootcamp.yatter2025.usecase.impl.post

import android.accounts.AuthenticatorException
import com.dmm.bootcamp.yatter2025.domain.repository.YweetRepository
import com.dmm.bootcamp.yatter2025.usecase.post.PostStatusUseCase
import com.dmm.bootcamp.yatter2025.usecase.post.PostStatusUseCaseResult
import java.io.File

class PostStatusUseCaseImpl(
  private val yweetRepository: YweetRepository
) : PostStatusUseCase {
  override suspend fun execute(
    content: String,
    attachmentList: List<File>
  ): PostStatusUseCaseResult {
    if (content == "" && attachmentList.isEmpty()) {
      return PostStatusUseCaseResult.Failure.EmptyContent
    }

    return try {
      yweetRepository.create(
        content = content,
        attachmentList = emptyList()
      )

      PostStatusUseCaseResult.Success
    } catch (e: AuthenticatorException) {
      PostStatusUseCaseResult.Failure.NotLoggedIn
    } catch (e: Exception) {
      PostStatusUseCaseResult.Failure.OtherError(e)
    }
  }
}
