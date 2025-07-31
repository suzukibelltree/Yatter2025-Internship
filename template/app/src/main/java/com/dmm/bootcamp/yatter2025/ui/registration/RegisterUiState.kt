package com.dmm.bootcamp.yatter2025.ui.registration

import com.dmm.bootcamp.yatter2025.ui.registration.bindingmodel.UserBindingModel

data class RegisterUiState(
    val userBindingModel: UserBindingModel,
    val validUsername: Boolean,
    val validPassword: Boolean,
    val isLoading: Boolean
) {
    val isEnableRegister: Boolean = validUsername && validPassword

    companion object {
        fun empty(): RegisterUiState = RegisterUiState(
            userBindingModel = UserBindingModel(
                username = "",
                password = ""
            ),
            validUsername = false,
            validPassword = false,
            isLoading = false
        )
    }
}