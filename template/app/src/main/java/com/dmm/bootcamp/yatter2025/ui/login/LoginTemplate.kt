package com.dmm.bootcamp.yatter2025.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dmm.bootcamp.yatter2025.ui.theme.Yatter2025Theme

@Composable
fun LoginTemplate(
    userName: String,
    onChangedUserName: (String) -> Unit,
    password: String,
    onChangePassword: (String) -> Unit,
    isEnableLogin: Boolean,
    isLoading: Boolean,
    onClickLogin: () -> Unit,
    onClickRegister: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "ログイン") }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    text = "ユーザー名"
                )
                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    value = userName,
                    onValueChange = onChangedUserName,
                    placeholder = {
                        Text(text = "username")
                    }
                )
                Text(
                    text = "パスワード",
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    value = password,
                    onValueChange = onChangePassword,
                    placeholder = {
                        Text(text = "password")
                    }
                )
                Button(
                    enabled = isEnableLogin,
                    onClick = onClickLogin,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "ログイン"
                    )
                }
                Divider(modifier = Modifier.padding(vertical = 16.dp))

                Text(
                    text = "初めてご利用の方は",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.body2
                )
                TextButton(
                    onClick = onClickRegister,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "新規ユーザー登録")
                }
            }
            if (isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview
@Composable
private fun LoginTemplatePreview() {
    Yatter2025Theme {
        Surface {
            LoginTemplate(
                userName = "username",
                onChangedUserName = {},
                password = "password",
                onChangePassword = {},
                isEnableLogin = true,
                isLoading = false,
                onClickLogin = {},
                onClickRegister = {}
            )
        }
    }
}