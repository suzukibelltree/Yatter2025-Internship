package com.dmm.bootcamp.yatter2024.di

import com.dmm.bootcamp.yatter2024.auth.TokenProvider
import com.dmm.bootcamp.yatter2024.auth.TokenProviderImpl
import com.dmm.bootcamp.yatter2024.infra.api.YatterApiFactory
import com.dmm.bootcamp.yatter2024.infra.domain.converter.AccountConverter
import com.dmm.bootcamp.yatter2024.infra.domain.converter.StatusConverter
import com.dmm.bootcamp.yatter2024.infra.pref.MePreferences
import org.koin.dsl.module

internal val infraModule = module {
  single { MePreferences(get()) }
  single { YatterApiFactory().create() }

  factory<TokenProvider> { TokenProviderImpl(get()) }
}
