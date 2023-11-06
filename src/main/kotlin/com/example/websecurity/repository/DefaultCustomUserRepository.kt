package com.example.websecurity.repository

import com.example.websecurity.entity.CustomUserEntity
import org.springframework.stereotype.Repository

@Repository
class DefaultCustomUserRepository: CustomUserRepository {
    override fun findByLoginId(loginId: String): CustomUserEntity<*>? {
        throw RuntimeException("CustomUserRepository need to be implement")
    }
}