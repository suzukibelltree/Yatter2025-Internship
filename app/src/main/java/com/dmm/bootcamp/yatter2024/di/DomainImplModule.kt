package com.dmm.bootcamp.yatter2024.di

import com.dmm.bootcamp.yatter2024.domain.repository.UserRepository
import com.dmm.bootcamp.yatter2024.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2024.domain.service.CheckLoginService
import com.dmm.bootcamp.yatter2024.domain.service.GetLoginUserService
import com.dmm.bootcamp.yatter2024.domain.service.LoginService
import com.dmm.bootcamp.yatter2024.infra.domain.repository.UserRepositoryImpl
import com.dmm.bootcamp.yatter2024.infra.domain.repository.StatusRepositoryImpl
import com.dmm.bootcamp.yatter2024.infra.domain.service.CheckLoginServiceImpl
import com.dmm.bootcamp.yatter2024.infra.domain.service.GetLoginUserServiceImpl
import com.dmm.bootcamp.yatter2024.infra.domain.service.LoginServiceImpl
import org.koin.dsl.module

internal val domainImplModule = module {
  single<UserRepository> { UserRepositoryImpl(get(), get()) }
  single<StatusRepository> { StatusRepositoryImpl(get(), get()) }

  factory<GetLoginUserService> { GetLoginUserServiceImpl(get()) }
  factory<LoginService> { LoginServiceImpl(get(), get()) }
  factory<CheckLoginService> { CheckLoginServiceImpl(get()) }
}
