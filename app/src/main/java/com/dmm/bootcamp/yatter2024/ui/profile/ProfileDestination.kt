package com.dmm.bootcamp.yatter2024.ui.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dmm.bootcamp.yatter2024.common.navigation.Destination

class ProfileDestination(
  private val username: String?
) : Destination(route) {
  override fun buildRoute(): String {
    return buildString {
      append("profile")
      if (username != null) {
        append("?username=$username")
      }
    }
  }

  companion object {
    private const val route = "profile?username={username}"

    fun createNode(builder: NavGraphBuilder) {
      builder.composable(
        route = route,
        arguments = listOf(
          navArgument("username") {
            nullable = true
          },
        ),
      ) { backStackEntry ->
        val username = backStackEntry.arguments?.getString("username")
        ProfilePage(username)
      }
    }
  }
}
