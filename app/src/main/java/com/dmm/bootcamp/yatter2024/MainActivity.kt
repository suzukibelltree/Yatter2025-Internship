package com.dmm.bootcamp.yatter2024

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.dmm.bootcamp.yatter2024.ui.MainApp
import com.dmm.bootcamp.yatter2024.ui.theme.Yatter2024Theme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

  private val viewModel: MainViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      Yatter2024Theme {
        MainApp(viewModel = viewModel)
      }
    }
  }
}
