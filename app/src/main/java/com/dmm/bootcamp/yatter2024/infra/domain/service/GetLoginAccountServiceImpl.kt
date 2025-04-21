package com.dmm.bootcamp.yatter2024.infra.domain.service

import com.dmm.bootcamp.yatter2024.domain.model.Account
import com.dmm.bootcamp.yatter2024.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2024.domain.service.GetLoginAccountService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetLoginAccountServiceImpl(
  private val accountRepository: AccountRepository,
) : GetLoginAccountService {
  override suspend fun execute(): Account? = withContext(Dispatchers.IO) {
    accountRepository.findLoginUser()
  }
}
