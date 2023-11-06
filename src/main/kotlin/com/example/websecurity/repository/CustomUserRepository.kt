package com.example.websecurity.repository

import com.example.websecurity.entity.CustomUserEntity
import org.springframework.stereotype.Repository

@Repository
interface CustomUserRepository {
    fun findByLoginId(loginId: String): CustomUserEntity<*>?
}