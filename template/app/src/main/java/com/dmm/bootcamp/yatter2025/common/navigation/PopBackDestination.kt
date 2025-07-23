package com.dmm.bootcamp.yatter2025.common.navigation

import androidx.navigation.NavController

object PopBackDestination : Destination("popback") {
  override fun navigate(navController: NavController) {
    navController.popBackStack()
  }
}
