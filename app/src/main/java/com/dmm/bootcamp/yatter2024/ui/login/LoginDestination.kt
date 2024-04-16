package com.dmm.bootcamp.yatter2024.ui.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dmm.bootcamp.yatter2024.common.navigation.Destination

class LoginDestination : Destination(route) {
  companion object {
    private const val route = "Login"

    fun createNode(builder: NavGraphBuilder) {
      builder.composable(route) {
        LoginPage()
      }
    }
  }
}
