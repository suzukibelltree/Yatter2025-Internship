package com.dmm.bootcamp.yatter2024.ui.post

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import com.dmm.bootcamp.yatter2024.ui.theme.yatter2024Theme
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostActivity: AppCompatActivity() {
  companion object {
    fun newIntent(context: Context): Intent = Intent(
      context,
      PostActivity::class.java,
    )
  }
  private val viewModel: PostViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      yatter2024Theme {
        Surface {
          PostPage(viewModel = viewModel)
        }
      }
    }

    viewModel.onCreate()

    viewModel.goBack.observe(this) {
      finish()
    }
  }
}