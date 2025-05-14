package com.dmm.bootcamp.yatter2024.di

import com.dmm.bootcamp.yatter2024.usecase.impl.login.LoginUseCaseImpl
import com.dmm.bootcamp.yatter2024.usecase.impl.post.PostStatusUseCaseImpl
import com.dmm.bootcamp.yatter2024.usecase.impl.register.RegisterUserUseCaseImpl
import com.dmm.bootcamp.yatter2024.usecase.login.LoginUseCase
import com.dmm.bootcamp.yatter2024.usecase.post.PostStatusUseCase
import com.dmm.bootcamp.yatter2024.usecase.register.RegisterUserUseCase
import org.koin.dsl.module

internal val useCaseModule = module {
  factory<PostStatusUseCase> { PostStatusUseCaseImpl(get()) }
  factory<RegisterUserUseCase> { RegisterUserUseCaseImpl(get(), get(), get()) }
  factory<LoginUseCase> { LoginUseCaseImpl(get(), get()) }
}
