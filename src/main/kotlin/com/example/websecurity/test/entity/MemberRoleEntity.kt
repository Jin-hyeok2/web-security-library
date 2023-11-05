package com.example.websecurity.test.entity

import com.example.websecurity.status.Role
import jakarta.persistence.*

@Entity
class MemberRoleEntity(
    @Enumerated(EnumType.STRING)
    val role: Role,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    val memberEntity: MemberEntity,
) {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    var id: Long? = null
}