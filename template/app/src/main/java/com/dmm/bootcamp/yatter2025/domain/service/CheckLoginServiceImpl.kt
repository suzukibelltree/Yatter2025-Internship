package com.dmm.bootcamp.yatter2025.domain.service

import com.dmm.bootcamp.yatter2025.infra.pref.TokenPreferences

class CheckLoginServiceImpl(
    private val tokenPreferences: TokenPreferences
) : CheckLoginService {
    override suspend fun execute(): Boolean {
        return tokenPreferences.getAccessToken().isNullOrEmpty().not()
    }
}