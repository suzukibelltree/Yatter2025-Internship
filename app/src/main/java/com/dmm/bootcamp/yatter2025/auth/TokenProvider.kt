package com.dmm.bootcamp.yatter2025.auth

interface TokenProvider {
  suspend fun provide(): String
}