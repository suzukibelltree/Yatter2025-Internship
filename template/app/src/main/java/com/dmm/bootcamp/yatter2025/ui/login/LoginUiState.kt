package com.dmm.bootcamp.yatter2025.ui.login

import com.dmm.bootcamp.yatter2025.ui.login.bindingmodel.LoginBindingModel

/**
 * ログイン画面のUi状態
 * @param loginBindingModel:ログインに必要なモデル
 * @param isLoading: ロード中か否か
 * @param validUsername: ユーザー名の入力が適切か否か
 * @param validPassword: パスワードの入力が適切か否か
 */
data class LoginUiState(
    val loginBindingModel: LoginBindingModel,
    val isLoading: Boolean,
    val validUsername: Boolean,
    val validPassword: Boolean
) {
    val isEnableLogin: Boolean = validUsername && validPassword

    companion object {
        fun empty(): LoginUiState = LoginUiState(
            loginBindingModel = LoginBindingModel(
                username = "",
                password = ""
            ),
            isLoading = false,
            validUsername = false,
            validPassword = false
        )
    }
}