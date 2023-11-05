package com.example.websecurity.repository

import com.example.websecurity.entity.CustomUserEntity

interface CustomUserRepository {
    fun findByLoginId(loginId: String): CustomUserEntity<*>?
}