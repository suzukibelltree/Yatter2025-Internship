package com.dmm.bootcamp.yatter2024.ui.timeline

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import com.dmm.bootcamp.yatter2024.ui.post.PostActivity
import com.dmm.bootcamp.yatter2024.ui.profile.ProfileActivity
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class PublicTimelineActivity : AppCompatActivity() {
  companion object {
    fun newIntent(context: Context): Intent = Intent(
      context,
      PublicTimelineActivity::class.java,
    )
  }

  private val viewModel: PublicTimelineViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      Yatter2024Theme {
        Surface {
          PublicTimelinePage(viewModel = viewModel)
        }
      }
    }

    viewModel.navigateToPost.observe(this) {
      startActivity(PostActivity.newIntent(this))
    }

    viewModel.navigateToMyProfile.observe(this) {
      startActivity(ProfileActivity.newIntent(this))
    }
  }

  override fun onResume() {
    super.onResume()
    viewModel.onResume()
  }

}