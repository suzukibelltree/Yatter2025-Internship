package com.dmm.bootcamp.yatter2024.domain.service

import com.dmm.bootcamp.yatter2024.domain.model.Account
import com.dmm.bootcamp.yatter2024.domain.model.Relationship
import com.dmm.bootcamp.yatter2024.domain.model.Username

interface CheckRelationshipService {
  suspend fun execute(
    account: Account,
    usernameList: List<Username>
  ): List<Relationship>
}
