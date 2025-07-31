package com.dmm.bootcamp.yatter2025.ui.registration

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.dmm.bootcamp.yatter2025.common.navigation.Destination

class RegisterUserDestination(
    builder: (NavOptionsBuilder.() -> Unit)? = null
) : Destination(ROUTE, builder) {
    companion object {
        private const val ROUTE = "registerUser"

        fun createNode(builder: NavGraphBuilder) {
            builder.composable(ROUTE) {
                RegisterUserPage()
            }
        }
    }
}