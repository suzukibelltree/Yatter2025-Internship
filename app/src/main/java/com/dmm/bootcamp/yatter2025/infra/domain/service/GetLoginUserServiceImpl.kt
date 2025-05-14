package com.dmm.bootcamp.yatter2025.infra.domain.service

import com.dmm.bootcamp.yatter2025.domain.model.User
import com.dmm.bootcamp.yatter2025.domain.repository.UserRepository
import com.dmm.bootcamp.yatter2025.domain.service.GetLoginUserService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetLoginUserServiceImpl(
  private val userRepository: UserRepository,
) : GetLoginUserService {
  override suspend fun execute(): User? = withContext(Dispatchers.IO) {
    userRepository.findLoginUser(disableCache = false)
  }
}
