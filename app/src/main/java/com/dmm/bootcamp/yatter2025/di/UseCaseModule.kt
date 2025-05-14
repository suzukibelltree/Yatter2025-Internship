package com.dmm.bootcamp.yatter2025.di

import com.dmm.bootcamp.yatter2025.usecase.impl.login.LoginUseCaseImpl
import com.dmm.bootcamp.yatter2025.usecase.impl.post.PostStatusUseCaseImpl
import com.dmm.bootcamp.yatter2025.usecase.impl.register.RegisterUserUseCaseImpl
import com.dmm.bootcamp.yatter2025.usecase.login.LoginUseCase
import com.dmm.bootcamp.yatter2025.usecase.post.PostStatusUseCase
import com.dmm.bootcamp.yatter2025.usecase.register.RegisterUserUseCase
import org.koin.dsl.module

internal val useCaseModule = module {
  factory<PostStatusUseCase> { PostStatusUseCaseImpl(get()) }
  factory<RegisterUserUseCase> { RegisterUserUseCaseImpl(get(), get(), get()) }
  factory<LoginUseCase> { LoginUseCaseImpl(get(), get()) }
}
