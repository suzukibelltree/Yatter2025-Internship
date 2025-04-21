package com.dmm.bootcamp.yatter2024.domain.repository

import com.dmm.bootcamp.yatter2024.domain.model.Account
import com.dmm.bootcamp.yatter2024.domain.model.Password
import com.dmm.bootcamp.yatter2024.domain.model.Username
import java.net.URL

interface AccountRepository {
  suspend fun findLoginUser(): Account?

  suspend fun findByUsername(username: Username): Account?

  suspend fun create(
    username: Username,
    password: Password
  ): Account

  suspend fun update(
    me: Account,
    newDisplayName: String?,
    newNote: String?,
    newAvatar: URL?,
    newHeader: URL?
  ): Account

  suspend fun followings(): List<Account>
  suspend fun followers(): List<Account>

  suspend fun follow(me: Account, username: Username)
  suspend fun unfollow(me: Account, username: Username)
}
