package com.example.websecurity.test.repository

import com.example.websecurity.repository.CustomUserRepository
import com.example.websecurity.test.entity.MemberEntity
import com.example.websecurity.test.entity.MemberRoleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository: JpaRepository<MemberEntity, Long>, CustomUserRepository {
    override fun findByLoginId(loginId: String): MemberEntity?
}

interface MemberRoleRepository: JpaRepository<MemberRoleEntity, Long>