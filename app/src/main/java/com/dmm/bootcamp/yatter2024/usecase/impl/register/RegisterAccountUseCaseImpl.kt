package com.dmm.bootcamp.yatter2024.usecase.impl.register

import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2024.domain.service.LoginService
import com.dmm.bootcamp.yatter2024.infra.pref.LoginAccountPreferences
import com.dmm.bootcamp.yatter2024.usecase.register.RegisterAccountUseCase
import com.dmm.bootcamp.yatter2024.usecase.register.RegisterAccountUseCaseResult

class RegisterAccountUseCaseImpl(
  private val accountRepository: AccountRepository,
  private val loginAccountPreferences: LoginAccountPreferences,
  private val loginService: LoginService,
) : RegisterAccountUseCase {
  override suspend fun execute(
    username: String,
    password: String,
  ): RegisterAccountUseCaseResult {
    if (username == "") {
      return RegisterAccountUseCaseResult.Failure.EmptyUsername
    }
    if (password == "") {
      return RegisterAccountUseCaseResult.Failure.EmptyPassword
    }
    val newUsername = Username(username)
    val newPassword = Password(password)
    if (!newPassword.validate()) {
      return RegisterAccountUseCaseResult.Failure.InvalidPassword
    }

    runCatching {
      val me = accountRepository.create(
        newUsername,
        newPassword,
      )
      loginAccountPreferences.putUserName(me.username.value)
    }.onFailure {
      return RegisterAccountUseCaseResult.Failure.CreateAccountError(it)
    }

    runCatching {
      loginService.execute(
        newUsername,
        newPassword,
      )
    }.onFailure {
      return RegisterAccountUseCaseResult.Failure.LoginError(it)
    }

    return RegisterAccountUseCaseResult.Success
  }
}
