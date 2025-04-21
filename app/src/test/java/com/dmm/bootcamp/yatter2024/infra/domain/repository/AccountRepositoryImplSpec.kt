package com.dmm.bootcamp.yatter2024.infra.domain.repository

import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.infra.api.YatterApi
import com.dmm.bootcamp.yatter2024.infra.api.json.AccountJson
import com.dmm.bootcamp.yatter2024.infra.domain.converter.AccountConverter
import com.dmm.bootcamp.yatter2024.infra.pref.LoginAccountPreferences
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AccountRepositoryImplSpec {
  val yatterApi = mockk<YatterApi>()
  val loginAccountPreferences = mockk<LoginAccountPreferences>()
  val subject = AccountRepositoryImpl(yatterApi, loginAccountPreferences)

  @Test
  fun findByUsername() = runTest {
    val username = Username("username")
    val accountJson = AccountJson(
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

    val expect = AccountConverter.convertToDomainModel(accountJson)

    coEvery {
      yatterApi.getAccountByUsername(any())
    } returns accountJson

    val result = subject.findByUsername(username)

    coVerify {
      yatterApi.getAccountByUsername(username.value)
    }

    assertThat(result).isEqualTo(expect)
  }
}
