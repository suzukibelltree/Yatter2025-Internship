package com.dmm.bootcamp.yatter2024.infra.pref

import android.content.Context

class TokenPreferences(context: Context) {
  companion object {
    private const val PREF_NAME = "token"
    private const val KEY_ACCESS_TOKEN = "access_token"
  }

  private val sharedPreferences = context.getSharedPreferences(
    PREF_NAME,
    Context.MODE_PRIVATE
  )

  fun getAccessToken(): String? = sharedPreferences.getString(
    KEY_ACCESS_TOKEN,
    ""
  )

  fun putAccessToken(token: String?) {
    sharedPreferences.edit().putString(
      KEY_ACCESS_TOKEN,
      token
    ).apply()
  }

  fun clear() = sharedPreferences.edit().clear().apply()
}
