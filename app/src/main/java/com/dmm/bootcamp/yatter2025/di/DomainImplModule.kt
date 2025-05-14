package com.dmm.bootcamp.yatter2025.di

import com.dmm.bootcamp.yatter2025.domain.repository.UserRepository
import com.dmm.bootcamp.yatter2025.domain.repository.YweetRepository
import com.dmm.bootcamp.yatter2025.domain.service.CheckLoginService
import com.dmm.bootcamp.yatter2025.domain.service.GetLoginUserService
import com.dmm.bootcamp.yatter2025.domain.service.LoginService
import com.dmm.bootcamp.yatter2025.infra.domain.repository.UserRepositoryImpl
import com.dmm.bootcamp.yatter2025.infra.domain.repository.YweetRepositoryImpl
import com.dmm.bootcamp.yatter2025.infra.domain.service.CheckLoginServiceImpl
import com.dmm.bootcamp.yatter2025.infra.domain.service.GetLoginUserServiceImpl
import com.dmm.bootcamp.yatter2025.infra.domain.service.LoginServiceImpl
import org.koin.dsl.module

internal val domainImplModule = module {
  single<UserRepository> { UserRepositoryImpl(get(), get()) }
  single<YweetRepository> { YweetRepositoryImpl(get(), get(), get()) }

  factory<GetLoginUserService> { GetLoginUserServiceImpl(get()) }
  factory<LoginService> { LoginServiceImpl(get(), get()) }
  factory<CheckLoginService> { CheckLoginServiceImpl(get()) }
}
