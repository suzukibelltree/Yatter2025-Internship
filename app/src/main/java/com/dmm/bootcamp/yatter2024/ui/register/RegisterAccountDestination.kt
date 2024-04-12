package com.dmm.bootcamp.yatter2024.ui.register

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.dmm.bootcamp.yatter2024.common.navigation.Destination

class RegisterAccountDestination : Destination(route) {
  companion object {
    private const val route = "registerAccount"

    fun createNode(builder: NavGraphBuilder) {
      builder.composable(route) {
        RegisterAccountPage()
      }
    }
  }
}
