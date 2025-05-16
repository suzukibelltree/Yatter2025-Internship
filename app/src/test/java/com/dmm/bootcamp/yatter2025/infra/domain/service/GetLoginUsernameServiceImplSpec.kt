package com.dmm.bootcamp.yatter2025.infra.domain.service

import com.dmm.bootcamp.yatter2025.domain.model.Username
import com.dmm.bootcamp.yatter2025.infra.pref.LoginUserPreferences
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetLoginUsernameServiceImplSpec {
  private val loginUserPreferences = mockk<LoginUserPreferences>()
  private val subject = GetLoginUsernameServiceImpl(loginUserPreferences)

  @Test
  fun getLoginUsername() {
    val username = "username"

    coEvery { loginUserPreferences.getUsername() } returns username

    val result = runBlocking { subject.execute() }

    assertThat(result).isEqualTo(Username(value = username))
  }

  @Test
  fun getLoginUsernameNull() {
    coEvery { loginUserPreferences.getUsername() } returns null

    val result = runBlocking { subject.execute() }

    assertThat(result).isNull()
  }
}
