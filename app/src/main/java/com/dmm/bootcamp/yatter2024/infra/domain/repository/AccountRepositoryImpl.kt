package com.dmm.bootcamp.yatter2024.infra.domain.repository

import android.util.Log
import com.dmm.bootcamp.yatter2024.domain.model.Account
import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2024.infra.api.YatterApi
import com.dmm.bootcamp.yatter2024.infra.api.json.CreateAccountJson
import com.dmm.bootcamp.yatter2024.infra.domain.converter.AccountConverter
import com.dmm.bootcamp.yatter2024.infra.pref.LoginAccountPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.URL

class AccountRepositoryImpl(
  private val yatterApi: YatterApi,
  private val loginAccountPreferences: LoginAccountPreferences,
) : AccountRepository {
  override suspend fun create(
    username: Username,
    password: Password
  ): Account = withContext(Dispatchers.IO) {
    val accountJson = yatterApi.createNewAccount(
      CreateAccountJson(
        username = username.value,
        password = password.value
      )
    )

    AccountConverter.convertToDomainModel(accountJson)
  }

  override suspend fun findLoginUser(): Account? = withContext(Dispatchers.IO) {
    val username = loginAccountPreferences.getUsername() ?: return@withContext null
    if (username.isEmpty()) return@withContext null
    findByUsername(username = Username(username))
  }

  override suspend fun findByUsername(username: Username): Account? = withContext(Dispatchers.IO) {
    try {
      val accountJson = yatterApi.getAccountByUsername(username = username.value)
      AccountConverter.convertToDomainModel(accountJson)
    } catch (e: HttpException) {
      Log.d("AccountRepositoryImpl", "HTTP error: ${e.code()} message:${e.message()}")
      null
    } catch (e: Exception) {
      Log.d("AccountRepositoryImpl", "Error: ${e.message}")
      null
    }
  }

  override suspend fun update(
    me: Account,
    newDisplayName: String?,
    newNote: String?,
    newAvatar: URL?,
    newHeader: URL?
  ): Account {
    TODO("Not yet implemented")
  }

  override suspend fun followings(): List<Account> {
    TODO("Not yet implemented")
  }

  override suspend fun followers(): List<Account> {
    TODO("Not yet implemented")
  }

  override suspend fun follow(me: Account, username: Username) {
    TODO("Not yet implemented")
  }

  override suspend fun unfollow(me: Account, username: Username) {
    TODO("Not yet implemented")
  }
}
