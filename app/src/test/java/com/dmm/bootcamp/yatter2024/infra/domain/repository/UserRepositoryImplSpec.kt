package com.dmm.bootcamp.yatter2024.infra.domain.repository

import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.infra.api.YatterApi
import com.dmm.bootcamp.yatter2024.infra.api.json.UserJson
import com.dmm.bootcamp.yatter2024.infra.domain.converter.UserConverter
import com.dmm.bootcamp.yatter2024.infra.pref.LoginUserPreferences
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UserRepositoryImplSpec {
  val yatterApi = mockk<YatterApi>()
  val loginUserPreferences = mockk<LoginUserPreferences>()
  val subject = UserRepositoryImpl(yatterApi, loginUserPreferences)

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
      createAt = ""
    )

    val expect = UserConverter.convertToDomainModel(userJson)

    coEvery {
      yatterApi.getUserByUsername(any())
    } returns userJson

    val result = subject.findByUsername(username)

    coVerify {
      yatterApi.getUserByUsername(username.value)
    }

    assertThat(result).isEqualTo(expect)
  }
}
