package com.dmm.bootcamp.yatter2024.domain.model

import com.dmm.bootcamp.yatter2024.common.ddd.Entity
import java.net.URL

data class Account(
  override val id: AccountId,
  val username: Username,
  val displayName: String?,
  val note: String?,
  val avatar: URL,
  val header: URL,
  val followingCount: Int,
  val followerCount: Int,
) : Entity<AccountId>(id)
