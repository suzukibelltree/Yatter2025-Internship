package com.dmm.bootcamp.yatter2024.infra.domain.service

import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.infra.api.YatterApi
import com.dmm.bootcamp.yatter2024.infra.api.json.LoginRequestBodyJson
import com.dmm.bootcamp.yatter2024.infra.api.json.LoginResponseJson
import com.dmm.bootcamp.yatter2024.infra.pref.MePreferences
import com.dmm.bootcamp.yatter2024.infra.pref.TokenPreferences
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.justRun
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LoginServiceImplSpec {
  private val yatterApi = mockk<YatterApi>()
  private val tokenPreferences = mockk<TokenPreferences>()
  private val subject = LoginServiceImpl(yatterApi, tokenPreferences)

  @Test
  fun saveUsername() = runTest {
    val username = Username("username")
    val password = Password("Password1%")

    coEvery {
      yatterApi.login(any())
    } returns LoginResponseJson(username.value)
    justRun {
      tokenPreferences.putAccessToken(any())
    }

    subject.execute(username, password)

    coVerify {
      yatterApi.login(LoginRequestBodyJson(username.value, password.value))
      tokenPreferences.putAccessToken(username.value)
    }
  }
}