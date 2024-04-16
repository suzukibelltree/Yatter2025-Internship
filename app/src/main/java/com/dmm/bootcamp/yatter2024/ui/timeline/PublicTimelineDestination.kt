package com.dmm.bootcamp.yatter2024.ui.timeline

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.dmm.bootcamp.yatter2024.common.navigation.Destination

class PublicTimelineDestination(
  builder: (NavOptionsBuilder.() -> Unit)? = null,
) : Destination(route, builder) {
  companion object {
    private const val route = "publicTimeline"

    fun createNode(builder: NavGraphBuilder) {
      builder.composable(route) {
        PublicTimelinePage()
      }
    }
  }
}
