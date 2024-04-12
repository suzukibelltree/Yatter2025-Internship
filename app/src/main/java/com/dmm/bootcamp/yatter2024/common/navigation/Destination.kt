package com.dmm.bootcamp.yatter2024.common.navigation

import androidx.navigation.NavController

abstract class Destination(
  val route: String,
) {
  open fun buildRoute(): String = route
  open fun navigate(navController: NavController) {
    navController.navigate(buildRoute())
  }
}
