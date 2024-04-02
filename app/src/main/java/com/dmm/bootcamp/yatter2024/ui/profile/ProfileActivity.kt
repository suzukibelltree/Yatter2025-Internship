package com.dmm.bootcamp.yatter2024.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import com.dmm.bootcamp.yatter2024.ui.theme.yatter2024Theme
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProfileActivity : ComponentActivity() {
  companion object {
    private val ARGS_USERNAME = "args_username"

    fun newIntent(
      context: Context,
      username: String? = null,
    ): Intent = Intent(
      context,
      ProfileActivity::class.java
    ).apply {
      putExtra(
        ARGS_USERNAME,
        username,
      )
    }
  }

  private val viewModel: ProfileViewModel by viewModel {
    parametersOf(intent.getStringExtra(ARGS_USERNAME))
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      yatter2024Theme {
        Surface {
          ProfilePage(viewModel = viewModel)
        }
      }
    }

    viewModel.goBack.observe(this) {
      finish()
    }
  }
}