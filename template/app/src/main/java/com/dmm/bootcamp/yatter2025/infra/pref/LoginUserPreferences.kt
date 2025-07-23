package com.dmm.bootcamp.yatter2025.infra.pref

import android.content.Context
import androidx.core.content.edit

class LoginUserPreferences(context: Context) {
  companion object {
    private const val PREF_NAME = "login_user"
    private const val KEY_USERNAME = "username"
  }

  private val sharedPreferences = context.getSharedPreferences(
    PREF_NAME,
    Context.MODE_PRIVATE
  )

  fun getUsername(): String? = sharedPreferences.getString(
    KEY_USERNAME,
    ""
  )

  fun putUsername(username: String?) {
    sharedPreferences.edit {
      putString(
        KEY_USERNAME,
        username
      )
    }
  }

  fun clear() = sharedPreferences.edit {
    clear()
  }
}
