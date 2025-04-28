package com.dmm.bootcamp.yatter2024.infra.domain.repository

import android.util.Log
import com.dmm.bootcamp.yatter2024.domain.model.User
import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.domain.repository.UserRepository
import com.dmm.bootcamp.yatter2024.infra.api.YatterApi
import com.dmm.bootcamp.yatter2024.infra.api.json.CreateUserJson
import com.dmm.bootcamp.yatter2024.infra.domain.converter.UserConverter
import com.dmm.bootcamp.yatter2024.infra.pref.LoginUserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.URL

class UserRepositoryImpl(
  private val yatterApi: YatterApi,
  private val loginUserPreferences: LoginUserPreferences,
) : UserRepository {
  override suspend fun create(
    username: Username,
    password: Password
  ): User = withContext(Dispatchers.IO) {
    val userJson = yatterApi.createNewUser(
      CreateUserJson(
        username = username.value,
        password = password.value
      )
    )

    UserConverter.convertToDomainModel(userJson)
  }

  override suspend fun findLoginUser(): User? = withContext(Dispatchers.IO) {
    val username = loginUserPreferences.getUsername() ?: return@withContext null
    if (username.isEmpty()) return@withContext null
    findByUsername(username = Username(username))
  }

  override suspend fun findByUsername(username: Username): User? = withContext(Dispatchers.IO) {
    try {
      val userJson = yatterApi.getUserByUsername(username = username.value)
      UserConverter.convertToDomainModel(userJson)
    } catch (e: HttpException) {
      Log.d("UserRepositoryImpl", "HTTP error: ${e.code()} message:${e.message()}")
      null
    } catch (e: Exception) {
      Log.d("UserRepositoryImpl", "Error: ${e.message}")
      null
    }
  }

  override suspend fun update(
    me: User,
    newDisplayName: String?,
    newNote: String?,
    newAvatar: URL?,
    newHeader: URL?
  ): User {
    TODO("Not yet implemented")
  }

  override suspend fun followings(): List<User> {
    TODO("Not yet implemented")
  }

  override suspend fun followers(): List<User> {
    TODO("Not yet implemented")
  }

  override suspend fun follow(me: User, username: Username) {
    TODO("Not yet implemented")
  }

  override suspend fun unfollow(me: User, username: Username) {
    TODO("Not yet implemented")
  }
}
