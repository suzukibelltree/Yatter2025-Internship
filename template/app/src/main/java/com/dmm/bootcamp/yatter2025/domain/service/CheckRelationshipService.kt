package com.dmm.bootcamp.yatter2025.domain.service

import com.dmm.bootcamp.yatter2025.domain.model.User
import com.dmm.bootcamp.yatter2025.domain.model.Relationship
import com.dmm.bootcamp.yatter2025.domain.model.Username

interface CheckRelationshipService {
  suspend fun execute(
    user: User,
    usernameList: List<Username>
  ): List<Relationship>
}
