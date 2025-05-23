package com.dmm.bootcamp.yatter2025.infra.domain.service

import com.dmm.bootcamp.yatter2025.domain.model.Username
import com.dmm.bootcamp.yatter2025.domain.service.GetLoginUsernameService
import com.dmm.bootcamp.yatter2025.infra.pref.LoginUserPreferences

class GetLoginUsernameServiceImpl(
  private val loginUserPreferences: LoginUserPreferences,
) : GetLoginUsernameService {
  override fun execute(): Username? = loginUserPreferences.getUsername()?.let { Username(it) }
}
