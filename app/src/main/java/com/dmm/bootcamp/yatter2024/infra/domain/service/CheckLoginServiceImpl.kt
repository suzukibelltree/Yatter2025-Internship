package com.dmm.bootcamp.yatter2024.infra.domain.service

import com.dmm.bootcamp.yatter2024.domain.service.CheckLoginService
import com.dmm.bootcamp.yatter2024.infra.pref.MePreferences

class CheckLoginServiceImpl(
  private val mePreferences: MePreferences
) : CheckLoginService {
  override suspend fun execute(): Boolean {
    return mePreferences.getUsername() != ""
  }
}
