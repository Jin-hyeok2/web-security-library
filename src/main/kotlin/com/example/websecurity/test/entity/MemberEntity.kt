package com.example.websecurity.test.entity

import com.example.websecurity.entity.CustomUserEntity
import com.example.websecurity.status.Gender
import com.example.websecurity.status.Role
import com.example.websecurity.test.dto.MemberDtoResponse
import jakarta.persistence.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity
@Table(name = "members", uniqueConstraints = [UniqueConstraint(columnNames = ["loginId"])])
class MemberEntity(
    @Column(nullable = false, updatable = false)
    val loginId: String,
    @Column(nullable = false)
    val password: String,
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    val birthDate: LocalDate,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val gender: Gender,
    @Column(nullable = false)
    val email: String,
): CustomUserEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @OneToMany(mappedBy = "memberEntity", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var memberRoles: List<MemberRoleEntity>? = listOf()

    fun toDto(): MemberDtoResponse = MemberDtoResponse(
        id = id!!,
        loginId = loginId,
        name = name,
        birthDate = birthDate.formatDate(),
        gender = gender.desc,
        email = email
    )

    private fun LocalDate.formatDate(): String =
        this.format(DateTimeFormatter.ofPattern("yyyyMMdd"))

    override fun id(): Long {
        return id!!
    }

    override fun loginId(): String {
        return loginId
    }

    override fun encodedPassword(): String {
        return password
    }

    override fun roles(): Collection<Role> {
        return memberRoles?.map { it.role }!!
    }
}