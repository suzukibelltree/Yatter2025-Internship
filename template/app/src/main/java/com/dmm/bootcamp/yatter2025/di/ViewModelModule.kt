package com.dmm.bootcamp.yatter2025.di

import com.dmm.bootcamp.yatter2025.ui.MainViewModel
import com.dmm.bootcamp.yatter2025.ui.login.LoginViewModel
import com.dmm.bootcamp.yatter2025.ui.post.PostViewModel
import com.dmm.bootcamp.yatter2025.ui.registration.RegisterUserViewModel
import com.dmm.bootcamp.yatter2025.ui.timeline.PublicTimeLineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { PublicTimeLineViewModel(get()) }
    viewModel { PostViewModel(get(), get()) }
    viewModel { RegisterUserViewModel(get()) }
    viewModel { LoginViewModel(get()) }
}
