package com.dmm.bootcamp.yatter2024.usecase.impl

import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.domain.service.LoginService
import com.dmm.bootcamp.yatter2024.infra.pref.LoginAccountPreferences
import com.dmm.bootcamp.yatter2024.usecase.impl.login.LoginUseCaseImpl
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCaseResult
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LoginUseCaseImplSpec {
  private val loginService = mockk<LoginService>()
  private val loginAccountPreferences = mockk<LoginAccountPreferences>()
  private val subject = LoginUseCaseImpl(loginService, loginAccountPreferences)

  @Test
  fun loginSuccess() = runTest {
    val username = Username("username")
    val password = Password("Password1%")

    coJustRun {
      loginService.execute(any(), any())
    }
    justRun {
      loginAccountPreferences.putUserName(any())
    }

    val result = subject.execute(username, password)

    coVerify {
      loginService.execute(username, password)
    }
    verify {
      loginAccountPreferences.putUserName(username.value)
    }

    assertThat(result).isEqualTo(LoginUseCaseResult.Success)
  }

  @Test
  fun loginFailureUsernameEmpty() = runTest {
    val username = Username("")
    val password = Password("Password1%")

    coJustRun {
      loginService.execute(any(), any())
    }
    justRun {
      loginAccountPreferences.putUserName(any())
    }

    val result = subject.execute(username, password)

    coVerify(inverse = true) {
      loginService.execute(any(), any())
    }
    verify(inverse = true) {
      loginAccountPreferences.putUserName(any())
    }

    assertThat(result).isEqualTo(LoginUseCaseResult.Failure.EmptyUsername)
  }

  @Test
  fun loginFailurePasswordEmpty() = runTest {
    val username = Username("username")
    val password = Password("")

    coJustRun {
      loginService.execute(any(), any())
    }
    justRun {
      loginAccountPreferences.putUserName(any())
    }

    val result = subject.execute(username, password)

    coVerify(inverse = true) {
      loginService.execute(any(), any())
    }
    verify(inverse = true) {
      loginAccountPreferences.putUserName(any())
    }

    assertThat(result).isEqualTo(LoginUseCaseResult.Failure.EmptyPassword)
  }

  @Test
  fun loginFailurePasswordInvalid() = runTest {
    val username = Username("username")
    val password = Password("password")

    coJustRun {
      loginService.execute(any(), any())
    }
    justRun {
      loginAccountPreferences.putUserName(any())
    }

    val result = subject.execute(username, password)

    coVerify(inverse = true) {
      loginService.execute(any(), any())
    }
    verify(inverse = true) {
      loginAccountPreferences.putUserName(any())
    }

    assertThat(result).isEqualTo(LoginUseCaseResult.Failure.InvalidPassword)
  }

  @Test
  fun loginFailurePasswordOther() = runTest {
    val username = Username("username")
    val password = Password("Password1%")
    val error = Exception()

    coEvery {
      loginService.execute(any(), any())
    } throws error
    justRun {
      loginAccountPreferences.putUserName(any())
    }

    val result = subject.execute(username, password)

    coVerify {
      loginService.execute(any(), any())
    }
    verify(inverse = true) {
      loginAccountPreferences.putUserName(any())
    }

    assertThat(result).isEqualTo(LoginUseCaseResult.Failure.OtherError(error))
  }
}
