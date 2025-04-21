package com.dmm.bootcamp.yatter2024.infra.domain.service

import com.dmm.bootcamp.yatter2024.domain.model.Account
import com.dmm.bootcamp.yatter2024.domain.model.AccountId
import com.dmm.bootcamp.yatter2024.domain.model.Username
import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.net.URL

class GetLoginAccountServiceImplSpec {
  private val accountRepository = mockk<AccountRepository>()
  private val subject = GetLoginAccountServiceImpl(accountRepository)

  @Test
  fun getLoginUser() {
    val account = Account(
      id = AccountId(value = ""),
      username = Username(value = ""),
      displayName = null,
      note = null,
      avatar = URL("https://www.google.com"),
      header = URL("https://www.google.com"),
      followingCount = 0,
      followerCount = 0
    )

    coEvery { accountRepository.findLoginUser() } returns account

    val result = runBlocking { subject.execute() }

    assertThat(result).isEqualTo(account)
  }
}
