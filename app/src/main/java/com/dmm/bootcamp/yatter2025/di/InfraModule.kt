package com.dmm.bootcamp.yatter2025.di

import com.dmm.bootcamp.yatter2025.auth.TokenProvider
import com.dmm.bootcamp.yatter2025.auth.TokenProviderImpl
import com.dmm.bootcamp.yatter2025.infra.api.YatterApiFactory
import com.dmm.bootcamp.yatter2025.infra.pref.LoginUserPreferences
import com.dmm.bootcamp.yatter2025.infra.pref.TokenPreferences
import org.koin.dsl.module

internal val infraModule = module {
  single { LoginUserPreferences(get()) }
  single { TokenPreferences(get()) }
  single { YatterApiFactory().create() }

  factory<TokenProvider> { TokenProviderImpl(get()) }
}
