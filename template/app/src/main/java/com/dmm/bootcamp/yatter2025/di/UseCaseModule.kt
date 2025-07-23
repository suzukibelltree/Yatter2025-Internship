package com.dmm.bootcamp.yatter2025.di

import com.dmm.bootcamp.yatter2025.usecase.impl.login.LoginUseCaseImpl
import com.dmm.bootcamp.yatter2025.usecase.impl.post.PostYweetUseCaseImpl
import com.dmm.bootcamp.yatter2025.usecase.impl.register.RegisterUserUseCaseImpl
import com.dmm.bootcamp.yatter2025.usecase.login.LoginUseCase
import com.dmm.bootcamp.yatter2025.usecase.post.PostYweetUseCase
import com.dmm.bootcamp.yatter2025.usecase.register.RegisterUserUseCase
import org.koin.dsl.module

internal val useCaseModule = module {
  factory<PostYweetUseCase> { PostYweetUseCaseImpl(get()) }
  factory<RegisterUserUseCase> { RegisterUserUseCaseImpl(get(), get(), get()) }
  factory<LoginUseCase> { LoginUseCaseImpl(get(), get()) }
}
