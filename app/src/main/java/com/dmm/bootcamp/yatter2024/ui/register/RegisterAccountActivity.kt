package com.dmm.bootcamp.yatter2024.ui.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import com.dmm.bootcamp.yatter2024.ui.login.LoginActivity
import com.dmm.bootcamp.yatter2024.ui.theme.yatter2024Theme
import com.dmm.bootcamp.yatter2024.ui.timeline.PublicTimelineActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterAccountActivity : ComponentActivity() {
  companion object {
    fun newIntent(context: Context) = Intent(
      context,
      RegisterAccountActivity::class.java,
    )
  }

  private val viewModel: RegisterAccountViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      yatter2024Theme {
        Surface {
          RegisterAccountPage(viewModel = viewModel)
        }
      }
    }

    viewModel.navigateToAllTimeLine.observe(this) {
      startActivity(PublicTimelineActivity.newIntent(this))
      finish()
    }

    viewModel.navigateToLogin.observe(this) {
      startActivity(LoginActivity.newIntent(this))
    }
  }
}
