package com.dmm.bootcamp.yatter2024.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import com.dmm.bootcamp.yatter2024.ui.register.RegisterAccountActivity
import com.dmm.bootcamp.yatter2024.ui.theme.yatter2024Theme
import com.dmm.bootcamp.yatter2024.ui.timeline.PublicTimelineActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : ComponentActivity() {
  companion object {
    fun newIntent(context: Context): Intent = Intent(
      context,
      LoginActivity::class.java,
    )
  }

  private val viewModel: LoginViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      yatter2024Theme {
        Surface {
          LoginPage(viewModel = viewModel)
        }
      }
    }

    viewModel.navigateToPublicTimeline.observe(this) {
      startActivity(PublicTimelineActivity.newIntent(this))
      finish()
    }

    viewModel.navigateToRegister.observe(this) {
      startActivity(RegisterAccountActivity.newIntent(this))
      finish()
    }
  }
}