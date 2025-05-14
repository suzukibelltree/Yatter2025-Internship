package com.dmm.bootcamp.yatter2025.usecase.impl

import android.accounts.AuthenticatorException
import com.dmm.bootcamp.yatter2025.domain.model.User
import com.dmm.bootcamp.yatter2025.domain.model.UserId
import com.dmm.bootcamp.yatter2025.domain.model.Yweet
import com.dmm.bootcamp.yatter2025.domain.model.YweetId
import com.dmm.bootcamp.yatter2025.domain.model.Username
import com.dmm.bootcamp.yatter2025.domain.repository.YweetRepository
import com.dmm.bootcamp.yatter2025.usecase.impl.post.PostYweetUseCaseImpl
import com.dmm.bootcamp.yatter2025.usecase.post.PostYweetUseCaseResult
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.URL

class PostYweetUseCaseImplSpec {
  private val yweetRepository = mockk<YweetRepository>()
  private val subject = PostYweetUseCaseImpl(yweetRepository)

  @Test
  fun postStatusWithSuccess() = runTest {
    val content = "content"

    val yweet = Yweet(
      id = YweetId(value = ""),
      user = User(
        id = UserId(value = ""),
        username = Username(value = ""),
        displayName = null,
        note = null,
        avatar = URL("https://www.google.com"),
        header = URL("https://www.google.com"),
        followingCount = 0,
        followerCount = 0,
        isMe = true,
      ),
      content = content,
      attachmentImageList = listOf(),
    )

    coEvery {
      yweetRepository.create(
        any(),
        any(),
      )
    } returns yweet

    val result = subject.execute(
      content,
      emptyList(),
    )

    coVerify {
      yweetRepository.create(
        content,
        emptyList(),
      )
    }

    assertThat(result).isEqualTo(PostYweetUseCaseResult.Success)
  }

  @Test
  fun postStatusWithEmptyContent() = runTest {
    val content = ""

    val result = subject.execute(
      content,
      emptyList(),
    )

    coVerify(inverse = true) {
      yweetRepository.create(
        any(),
        any(),
      )
    }

    assertThat(result).isEqualTo(PostYweetUseCaseResult.Failure.EmptyContent)
  }

  @Test
  fun postStatusWithNotLoggedIn() = runTest {
    val content = "content"

    coEvery {
      yweetRepository.create(
        any(),
        any(),
      )
    } throws AuthenticatorException()

    val result = subject.execute(
      content,
      emptyList(),
    )


    coVerify {
      yweetRepository.create(
        any(),
        any(),
      )
    }

    assertThat(result).isEqualTo(PostYweetUseCaseResult.Failure.NotLoggedIn)
  }

  @Test
  fun postStatusWithOtherError() = runTest {
    val content = "content"
    val exception = Exception()

    coEvery {
      yweetRepository.create(
        any(),
        any(),
      )
    } throws exception

    val result = subject.execute(
      content,
      emptyList(),
    )


    coVerify {
      yweetRepository.create(
        any(),
        any(),
      )
    }

    assertThat(result).isEqualTo(PostYweetUseCaseResult.Failure.OtherError(exception))
  }
}
