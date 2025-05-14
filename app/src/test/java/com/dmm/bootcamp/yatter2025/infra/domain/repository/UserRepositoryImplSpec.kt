package com.dmm.bootcamp.yatter2025.infra.domain.repository

import com.dmm.bootcamp.yatter2025.domain.model.Username
import com.dmm.bootcamp.yatter2025.infra.api.YatterApi
import com.dmm.bootcamp.yatter2025.infra.api.json.UserJson
import com.dmm.bootcamp.yatter2025.infra.domain.converter.UserConverter
import com.dmm.bootcamp.yatter2025.infra.pref.LoginUserPreferences
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UserRepositoryImplSpec {
  private val yatterApi = mockk<YatterApi>()
  private val loginUserPreferences = mockk<LoginUserPreferences>()
  private val subject = UserRepositoryImpl(yatterApi, loginUserPreferences)

  @Test
  fun findByUsername() = runTest {
    val username = Username("username")
    val userJson = UserJson(
      id = "id",
      username = "username",
      displayName = "display name",
      note = null,
      avatar = "https://www.google.com",
      header = "https://www.google.com",
      followingCount = 0,
      followersCount = 0,
      createdAt = ""
    )

    val expect = UserConverter.convertToDomainModel(userJson, isMe = true)

    coEvery {
      yatterApi.getUserByUsername(any())
    } returns userJson
    coEvery {
      loginUserPreferences.getUsername()
    } returns "username"

    val result = subject.findByUsername(username, disableCache = false)

    coVerify {
      yatterApi.getUserByUsername(username.value)
    }

    assertThat(result).isEqualTo(expect)
  }

  @Test
  fun findLoginUser() = runTest {
    val username = "username"
    val userJson = UserJson(
      id = "id",
      username = "username",
      displayName = "display name",
      note = null,
      avatar = "https://www.google.com",
      header = "https://www.google.com",
      followingCount = 0,
      followersCount = 0,
      createdAt = ""
    )
    val expect = UserConverter.convertToDomainModel(userJson, isMe = true)

    coEvery {
      loginUserPreferences.getUsername()
    } returns username
    coEvery {
      yatterApi.getUserByUsername(any())
    } returns userJson

    val result = subject.findLoginUser(disableCache = false)

    coVerify {
      yatterApi.getUserByUsername(username)
    }
    coVerify {
      loginUserPreferences.getUsername()
    }

    assertThat(result).isEqualTo(expect)
  }
}
