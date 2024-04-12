package com.dmm.bootcamp.yatter2024

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2024.common.navigation.Destination
import com.dmm.bootcamp.yatter2024.domain.service.CheckLoginService
import com.dmm.bootcamp.yatter2024.ui.login.LoginDestination
import com.dmm.bootcamp.yatter2024.ui.timeline.PublicTimelineDestination
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel(
  private val checkLoginService: CheckLoginService,
) : ViewModel() {

  private val _destination = MutableSharedFlow<Destination>()
  val destination: SharedFlow<Destination> = _destination.asSharedFlow()

  fun onCreate() {
    viewModelScope.launch {
      if (checkLoginService.execute()) {
        _destination.emit(PublicTimelineDestination())
      } else {
        _destination.emit(LoginDestination())
      }
    }
  }
}